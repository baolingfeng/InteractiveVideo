package cn.zju.blf.video;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.zju.blf.video.code.CodeAnalyzer;
import cn.zju.blf.video.code.CodeComparator;
import cn.zju.blf.video.code.CodePatch;
import cn.zju.blf.video.code.JDTCodeAnalyzer;
import cn.zju.blf.video.dao.AbstractEvent;

/*
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
*/

import cn.zju.blf.video.dao.FileContent;
import cn.zju.blf.video.dao.CodeOperation;
import cn.zju.blf.video.dao.EventComparator;
import cn.zju.blf.video.dao.LowLevelEvent;
import cn.zju.blf.video.db.SQLiteDBManager;
import cn.zju.blf.video.util.ActionTypes;
import cn.zju.blf.video.util.CompareUtil;
import cn.zju.blf.video.util.TimeUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class EventManager {
	SQLiteDBManager db;
	List<LowLevelEvent> mevents;
	List<LowLevelEvent> kevents;
	List<LowLevelEvent> events;
	
	String startTime;
	
	public EventManager(String videoName, String dbFile)
	{
		db = new SQLiteDBManager(dbFile);
		
		mevents = db.queryMouseEvent();
		kevents = db.queryKeyboardEvent();
		
		this.startTime = VideoMetadataManager.getInstance().getStartTime(videoName);//"2016-07-13 16:08:34.000";
		mergeEvents();
	}
	
	protected void mergeEvents()
	{
		events = new ArrayList<LowLevelEvent>();
		events.addAll(mevents);
		events.addAll(kevents);
		
		Collections.sort(events, new EventComparator());
		
		//startTime = events.get(0).getTimestamp();
		
		System.out.println(mevents.size() + "/" + kevents.size() + "/" + events.size()); 
		System.out.println(events.get(0));
		System.out.println(events.get(events.size()-1));
	}
	
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public List<AbstractEvent> summary()
	{
		List<LowLevelEvent> summaryEvents = new ArrayList<LowLevelEvent>();
		Map<String, List<CodePatch>> codes = new HashMap<String,List<CodePatch>>();
		Map<String, List<FileContent>> fileChanges = new HashMap<String, List<FileContent>>();
		List<FileContent> consoleOutputs = new ArrayList<FileContent>();
		boolean flag = true;
		
		//Map<String, Map<String, Object>> summaries = new HashMap<String, Map<String, Object>>();
		List<AbstractEvent> aes = new ArrayList<AbstractEvent>();
		
		for(int i=0; i<events.size(); i++)
		{
			LowLevelEvent e = events.get(i);
			
			
			if(e.getAction() == null ||!"eclipse.exe".equals(e.getProcessName())) continue;
			
			double interval = (TimeUtil.calcTimeDiff(startTime, e.getTimestamp()));
			
			if(e.getAction().getBoundLeft() < 0 || e.getAction().getBoundTop() < 0) continue;
			
			String parentActionName = e.getAction().getParentActionName();
			if("".equals(parentActionName)) continue;
			
			if(ActionTypes.isEqual("edit", e.getAction().getActionType()) && 
					ActionTypes.isEqual("pane", e.getAction().getParentActionType()))
			{
				if(parentActionName.endsWith(".java"))
				{
					CodePatch cp = CodeAnalyzer.parseJavaSource(e.getAction().getActionValue());
					//CodePatch cp = JDTCodeAnalyzer.parseJavaSource(e.getAction().getActionValue());
					if(cp == null) continue;
					
					cp.setFileName(parentActionName);
					if(codes.containsKey(parentActionName))
					{
						int len = codes.get(parentActionName).size();
						List<CodeOperation> changes = new ArrayList<CodeOperation>();
						if(!CodeComparator.compareCodePatch(codes.get(parentActionName).get(len-1), cp, changes))
						{
							codes.get(parentActionName).add(cp);
							//System.out.println("############");
							
							AbstractEvent ae = new AbstractEvent();
							ae.setTimestamp(e.getTimestamp());
							ae.setInterval(interval);
							ae.addExpression("codepatch", cp);
							ae.addExpression("codechanges", changes);
							ae.addExpression("action", e.getAction());
							aes.add(ae);
						}
					}
					else
					{
						List<CodePatch> l = new ArrayList<CodePatch>();
						l.add(cp);
						codes.put(parentActionName, l);
						
						AbstractEvent ae = new AbstractEvent();
						ae.setTimestamp(e.getTimestamp());
						ae.addExpression("codepatch", cp);
						ae.setInterval(interval);
						ae.addExpression("action", e.getAction());
						aes.add(ae);
					}
				}
				else if("Console".equals(parentActionName))
				{
					String exceptions = getExceptionInfoFromConsole(e.getAction().getActionValue());
					FileContent fc = new FileContent(e.getTimestamp(), "Console", exceptions);
					
					if(exceptions == null || "".equals(exceptions)) continue;
					
					int idx = consoleOutputs.lastIndexOf(fc);
					if(idx < 0)
					{
						consoleOutputs.add(fc);
						AbstractEvent ae = new AbstractEvent();
						ae.setTimestamp(e.getTimestamp());
						ae.setInterval(interval);
						ae.addExpression("console", exceptions);
						aes.add(ae);
					}
					else
					{
						double t = TimeUtil.calcTimeDiff(consoleOutputs.get(idx).getTimestamp(), e.getTimestamp());
						if(t > 300)
						{
							consoleOutputs.add(fc);
							AbstractEvent ae = new AbstractEvent();
							ae.setTimestamp(e.getTimestamp());
							ae.setInterval(interval);
							ae.addExpression("console", exceptions);
							aes.add(ae);
						}
					}
				}
				else // if(parentActionName.lastIndexOf('.') > 0)
				{
					if(fileChanges.containsKey(parentActionName))
					{
						int len = fileChanges.get(parentActionName).size();
						FileContent preFC = fileChanges.get(parentActionName).get(len - 1);
						double t = TimeUtil.calcTimeDiff(preFC.getTimestamp(), e.getTimestamp());
						
						if(t > 60 && !preFC.getContent().equals(e.getAction().getActionValue()))
						{
							//fileChanges.get(parentActionName).add(e.getAction().getActionValue());
							String diff = CompareUtil.compareText2(preFC.getContent(), e.getAction().getActionValue());
							
							AbstractEvent ae = new AbstractEvent();
							ae.setTimestamp(e.getTimestamp());
							ae.setInterval(interval);
							ae.addExpression("normalfile", parentActionName);
							ae.addExpression("normalfilediff", diff);
							ae.addExpression("action", e.getAction());
							aes.add(ae);
							
							fileChanges.get(parentActionName).add(new FileContent(e.getTimestamp(), parentActionName, e.getAction().getActionValue()));
						}
					}
					else
					{
						FileContent fc = new FileContent(e.getTimestamp(), parentActionName, e.getAction().getActionValue());
						List<FileContent> l = new ArrayList<FileContent>();
						l.add(fc);
						
						fileChanges.put(parentActionName, l);
						
						AbstractEvent ae = new AbstractEvent();
						ae.setTimestamp(e.getTimestamp());
						ae.setInterval(interval);
						ae.addExpression("normalfile", parentActionName);
						ae.addExpression("action", e.getAction());
						aes.add(ae);
					}
				}
				
			}
			else
			{
				//System.out.println(parentActionName);
				//System.out.println("############");
			}
			
			//System.out.println(component);
			
			summaryEvents.add(e);
		}
		//System.out.println(aes);
		return aes;
	}
	
	private String getExceptionInfoFromConsole(String content)
	{
		String regexStr = ".*\\..*Exception($| |:)";
		Pattern p = Pattern.compile(regexStr);
		
		content = content.replace("\\n", "\n");
		content = content.replace("\\t", "\t");
		
		String arr[] = content.split("\\n");
		List<String> res = new ArrayList<String>();
		for(int i=0; i<arr.length; i++)
		{
			Matcher m = p.matcher(arr[i]);
			int count = 0;
			while(m.find())
			{
				count++;
				
				String subseq = arr[i].substring(m.start(), m.end()).trim();
				
				arr[i] = m.replaceAll("<span class='exception'>" + subseq + "</span>");
			}
			if(count > 0) res.add(arr[i]);
		}
		
		return String.join(",", res);
	}
	
	public void estimateCodeChange()
	{
		Map<String, List<String>> fileChanges = new HashMap<String, List<String>>();
		Map<String, List<CodePatch>> codes = new HashMap<String,List<CodePatch>>();
		
		for(int i=0; i<events.size(); i++)
		{
			LowLevelEvent e = events.get(i);
			
			if(e.getAction() == null || !"eclipse.exe".equals(e.getProcessName()) )
			{
				continue;
			}
			
			String parentActionName = e.getAction().getParentActionName();
			
			if(!parentActionName.endsWith(".java"))
			{
				continue;
			}
			
			if(ActionTypes.isEqual("edit", e.getAction().getActionType()) && 
					ActionTypes.isEqual("pane", e.getAction().getParentActionType()))
			{
				CodePatch cp = CodeAnalyzer.parseJavaSource(e.getAction().getActionValue());
				if(cp == null) continue;
				
				if(codes.containsKey(parentActionName))
				{
					int len = codes.get(parentActionName).size();
					List<CodeOperation> changes = new ArrayList<CodeOperation>();
					if(!CodeComparator.compareCodePatch(codes.get(parentActionName).get(len-1), cp, changes))
					{
						codes.get(parentActionName).add(cp);
						System.out.println("############");
					}
				}
				else
				{
					List<CodePatch> l = new ArrayList<CodePatch>();
					l.add(cp);
					codes.put(parentActionName, l);
				}
				
				/*
				if(fileChanges.containsKey(parentActionName))
				{
					if(!fileChanges.get(parentActionName).equals(e.getAction().getActionValue()))
					{
						fileChanges.get(parentActionName).add(e.getAction().getActionValue());
						
						CodePatch cp = CodeAnalyzer.parseJavaSource(e.getAction().getActionValue());
						if(cp != null)
						{
							if(codes.containsKey(parentActionName))
							{
								int len = codes.get(parentActionName).size();
								CodeAnalyzer.compareCodePatch(codes.get(parentActionName).get(len-1), cp);
								codes.get(parentActionName).add(cp);
							}
							else
							{
								List<CodePatch> l = new ArrayList<CodePatch>();
								l.add(cp);
								codes.put(parentActionName, l);
							}
							//cp.getAPICalls();
						}
						
						System.out.println("############");
					}
				}
				else
				{
					List<String> contents = new ArrayList<String>();
					contents.add(e.getAction().getActionValue());
					fileChanges.put(parentActionName, contents);
					
					CodePatch cp = CodeAnalyzer.parseJavaSource(e.getAction().getActionValue());
					if(cp != null)
					{
						List<CodePatch> l = new ArrayList<CodePatch>();
						l.add(cp);
						codes.put(parentActionName, l);
					}
				}
				*/
			}
		}
		
		for(Entry<String, List<String>> entry : fileChanges.entrySet())
		{
			System.out.println(entry.getKey());
			System.out.println(entry.getValue().size());
		}
	}
	
	/*
	private void parseJavaSource(String str)
	{
		str = str.replace("\\n", "\n");
		str = str.replace("\\t", "\t");
		
		ASTParser parser = ASTParser.newParser(AST.JLS4);
		parser.setResolveBindings(true);
		parser.setSource(str.toCharArray());
		
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setEnvironment(System.getProperty("java.class.path").split(";"), null, null, true);
		
		//System.out.println(System.getProperty("java.class.path"));
		
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		
		Set<String> imports = new HashSet<String>();
		cu.accept(new ASTVisitor() {
			Set names = new HashSet();
 
			public boolean visit(VariableDeclarationFragment node) {
				SimpleName name = node.getName();
				this.names.add(name.getIdentifier());
				//System.out.println("Declaration of '" + name + "' at line"
				//		+ cu.getLineNumber(name.getStartPosition()));
				return false; // do not continue 
			}
			
			public boolean visit(ImportDeclaration node)
			{
				//System.out.println(node.getName());
				imports.add(node.getName().toString());
				return false;
			}
			
			//public boolean visit(ExpressionStatement node)
			//{
			//	System.out.println(node.getExpression() + "\n");
			//	return false;
			//}
			
			public boolean visit(MethodDeclaration node)
			{
				System.out.println(node.getName());
				Block block = node.getBody();
				 
				block.accept(new ASTVisitor() {
                    public boolean visit(MethodInvocation node) {
                    	System.out.println("Name: " + node.getName());
                    	
                    	Expression expression = node.getExpression();
                        if (expression != null) {
                            System.out.println("Expr: " + expression.toString());
                            ITypeBinding typeBinding = expression.resolveTypeBinding();
                            if (typeBinding != null) {
                                System.out.println("Type: " + typeBinding.getName());
                            }
                        }
                        IMethodBinding binding = node.resolveMethodBinding();
                        if (binding != null) {
                            ITypeBinding type = binding.getDeclaringClass();
                            if (type != null) {
                                System.out.println("Decl: " + type.getName());
                            }
                        }
                    	
                    	return true;
                    }
				});
				
				return false;
			}
			
			public boolean visit(SimpleName node) {
				if (this.names.contains(node.getIdentifier())) {
					//System.out.println("Usage of '" + node + "' at line "
					//		+ cu.getLineNumber(node.getStartPosition()));
				}
				return true;
			}
		});
	}
	*/
	public static void main(String args[])
	{
		//EventManager em = new EventManager(EventManager.class.getResource("/log.db3").getPath());
		//EventManager em = new EventManager("email","C:/baolingfeng/research/InteractiveVideo/data/example2/log.db3");
		//em.mergeEvents();
		//em.estimateCodeChange();
		//em.summary();
		
		String regexStr = ".*\\..*Exception($| |:)";
		Pattern p = Pattern.compile(regexStr);
		
		String s = "javax.mail.SendFailedException: No recipient addresses";
		Matcher m = p.matcher(s);
		
		int count = 0;
		while(m.find()) {
	         count++;
	         System.out.println("Match number "+count);
	         System.out.println("start(): "+m.start());
	         System.out.println("end(): "+m.end());
	         System.out.println(s.substring(m.start(), m.end()));
	         
	         System.out.println(m.replaceAll(" <span>" + s.substring(m.start(), m.end()).trim() + "</span> "));
	      }
	}
}
