package cn.zju.blf.video.code;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.zju.blf.video.dao.CodeOperation;

public class CodeComparator {
	private static String completeVariableType(String vtype, Set<String> imports)
	{
		for(String im : imports)
		{
			String[] arr = im.split("\\.");
			
			if(vtype.equals(arr[arr.length-1]))
			{
				return im;
			}
		}
		
		return vtype;
	}
	
	public static boolean compareCodePatch(CodePatch c1, CodePatch c2, List<CodeOperation> res)
	{
		boolean isEqual = true;
		
		Set<String> v1 = new HashSet<String>(c1.getVariables().keySet());
		Set<String> v2 = new HashSet<String>(c2.getVariables().keySet());
		Set<String> v3 = new HashSet<String>(v2);
		
		v2.removeAll(v1);
		if(v2.size() != 0)
		{
			isEqual = false;
			
			List<String> t = new ArrayList<String>();
			for(String s : v2)
			{
				t.add(s + "[" + completeVariableType(c2.getVariables().get(s), c2.getImports()) + "]");
				c2.getVariables().get(s);
			}
			res.add(new CodeOperation("add", "Variable", String.join(",", t)));
		}
		
		v1.removeAll(v3);
		if(v1.size() != 0)
		{
			isEqual = false;
			List<String> t = new ArrayList<String>();
			for(String s : v1)
			{
				t.add(s + "[" + completeVariableType(c1.getVariables().get(s), c1.getImports()) + "]");
				c1.getVariables().get(s);
			}
			res.add(new CodeOperation("delete", "Variable", String.join(",", t)));
		}
		
		Set<String> d1 = new HashSet<String>(c1.getDeclaration().keySet());
		Set<String> d2 = new HashSet<String>(c2.getDeclaration().keySet());
		Set<String> d3 = new HashSet<String>(d2);
		
		d2.removeAll(d1);
		if(d2.size() != 0)
		{
			isEqual = false;
			List<String> t = new ArrayList<String>();
			for(String s : d2)
			{
				t.add(s + "[" + c2.getDeclaration().get(s) + "]");
				c2.getDeclaration().get(s);
			}
			res.add(new CodeOperation("add", "Field", String.join(",", t)));
		}
		
		d1.removeAll(d3);
		if(d1.size() != 0)
		{
			isEqual = false;
			List<String> t = new ArrayList<String>();
			for(String s : d1)
			{
				t.add(s + "[" + c1.getDeclaration().get(s) + "]");
				c1.getDeclaration().get(s);
			}
			res.add(new CodeOperation("delete", "Field", String.join(",", t)));
		}
		
		List<String> a1 = c1.getAPICalls();
		List<String> a2 = c2.getAPICalls();
		List<String> a3 = new ArrayList<String>(a2);
		
		a2.removeAll(a1);
		if(a2.size() != 0)
		{
			isEqual = false;
			res.add(new CodeOperation("add", "APICall", String.join(",", a2)));
		}
		
		a1.removeAll(a3);
		if(a1.size() != 0)
		{
			isEqual = false;
			res.add(new CodeOperation("delete", "APICall", String.join(",", a1)));
		}
		
		Set<String> im1 = new HashSet<String>(c1.getImports());
		Set<String> im2 = new HashSet<String>(c2.getImports());
		Set<String> im3 = new HashSet<String>(im2);
		
		im2.removeAll(im1);
		if(im2.size() != 0)
		{
			isEqual = false;
			res.add(new CodeOperation("add", "Import", String.join(",", im2)));
		}
		
		im1.removeAll(im3);
		if(im1.size() != 0)
		{
			isEqual = false;
			res.add(new CodeOperation("delete", "Import", String.join(",", im1)));
		}
		
		//System.out.println(res);
		return isEqual;
	}
}
