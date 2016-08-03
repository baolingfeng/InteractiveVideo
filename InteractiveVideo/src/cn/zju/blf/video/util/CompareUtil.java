package cn.zju.blf.video.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import cn.zju.blf.video.util.diff_match_patch.Diff;
import cn.zju.blf.video.util.diff_match_patch.Patch;
import difflib.Delta;
import difflib.Delta.TYPE;
import difflib.DiffUtils;

public class CompareUtil {
	Logger logger = Logger.getLogger(CompareUtil.class.getName());
	
	private static List<String> fileToLines(String filename) {
        List<String> lines = new LinkedList<String>();
        String line = "";
        try {
                BufferedReader in = new BufferedReader(new FileReader(filename));
                while ((line = in.readLine()) != null) {
                        lines.add(line);
                }
        } catch (IOException e) {
                e.printStackTrace();
        }
        return lines;
	}
	
	private static List<String> textToLines(String text)
	{
		String temp = text.replaceAll("\\\\n", "\n");
		temp = temp.replaceAll("\\\\t", "\t");
		
		return Arrays.asList(temp.split("\n"));
	}
	
	public static String compareText(String text1, String text2) 
	{
		 List<String> original = textToLines(text1);
         List<String> revised  = textToLines(text2);
         
         difflib.Patch patch = DiffUtils.diff(original, revised);
         
         String change = "";
         for (Delta delta: patch.getDeltas()) 
         {
        	 change += delta.toString() + "\n";
         }
         
         return change;
	}
	
	public static String compareText2(String text1, String text2)
	{
		diff_match_patch dmp = new diff_match_patch();
        LinkedList<Diff> diffs = dmp.diff_main(text1, text2);
        //LinkedList<Patch> patches = dmp.patch_make(text1, text2);
        
        //System.out.println(URLDecoder.decode(dmp.patch_toText(patches)));
        
        //return URLDecoder.decode(dmp.patch_toText(patches));
        return dmp.diff_prettyHtml(diffs);
	}
	
	public static String compareTextInDetail(String text1, String text2)
	{
		List<String> original = textToLines(text1);
        List<String> revised  = textToLines(text2);
        
        difflib.Patch patch = DiffUtils.diff(original, revised);
        
        //List<CodeChangeDetail> list = new ArrayList<CodeChangeDetail>();
        
        String content = "";
        for (Delta delta: patch.getDeltas()) 
        {
        	//CodeChangeDetail c = new CodeChangeDetail();
        	//c.setType(delta.getType().toString() + " (Position: " + delta.getOriginal().getPosition() + ")");
        	
        	 if(delta.getType() == TYPE.INSERT)
        	 {
        		 for(int i=0; i<delta.getRevised().getLines().size(); i++)
	        	 {
        			 content += delta.getRevised().getLines().get(i) + "\n";
	        	 }
        	 }
        	 else if(delta.getType() == TYPE.DELETE)
        	 {
        		 for(int i=0; i<delta.getOriginal().getLines().size(); i++)
	        	 {
        			 content += delta.getOriginal().getLines().get(i) + "\n";
	        	 }
        	 }
        	 else if(delta.getType() == TYPE.CHANGE)
        	 {
        		 content += "Original: \n";
        		 for(int i=0; i<delta.getOriginal().getLines().size(); i++)
	        	 {
        			 content += delta.getOriginal().getLines().get(i) + "\n";
	        	 }
        		 
        		 content += "Revised: \n";
        		 for(int i=0; i<delta.getRevised().getLines().size(); i++)
	        	 {
        			 content += delta.getRevised().getLines().get(i) + "\n";
	        	 } 
        	 }
        	 
        	 //c.setContent(content);
        	 //list.add(c);
        }
        
        return content;
	}
	
	public static String compareTextInHtml(String text1, String text2) 
	{
		 List<String> original = textToLines(text1);
         List<String> revised  = textToLines(text2);
         
         difflib.Patch patch = DiffUtils.diff(original, revised);
         if(patch.getDeltas().size() == 0)
         {
        	 return "";
         }
         
         StringBuffer change = new StringBuffer();
         change.append("<table id='tblchange'>\n");
         change.append("<tr><td>Chnage Type</td><td>Content</td></tr>\n");
         
         for (Delta delta: patch.getDeltas()) 
         {
        	 change.append("<tr>");
        	 //change += delta.getType().toString() + "(Position: " + delta.getOriginal().getPosition() + ")\n";
        	 //change += "Lines: \n";
        	 if(delta.getType() == TYPE.INSERT)
        	 {
        		 change.append("<td class='insertcode'>");
        		 change.append("<label>"+delta.getType().toString()+"</label>");
        		 change.append(" (Position: " +  delta.getOriginal().getPosition() + ")</td><td>");
        		 for(int i=0; i<delta.getRevised().getLines().size(); i++)
	        	 {
        			 change.append(delta.getRevised().getLines().get(i) +"<br>");
	        	 }
        		 change.append("</td></tr>");
        	 }
        	 else if(delta.getType() == TYPE.DELETE)
        	 {
        		 change.append("<td class='deletecode'>");
        		 change.append("<label>"+delta.getType().toString()+"</label>");
        		 change.append(" (Position: " +  delta.getOriginal().getPosition() + ")</td><td>");
        		 for(int i=0; i<delta.getOriginal().getLines().size(); i++)
	        	 {
        			 change.append(delta.getOriginal().getLines().get(i) +"<br>");
	        	 }
        	 }
        	 else if(delta.getType() == TYPE.CHANGE)
        	 {
        		 change.append("<td class='changecode'>");
        		 change.append("<label>"+delta.getType().toString()+"</label>");
        		 change.append(" (Position: " +  delta.getOriginal().getPosition() + ")</td><td>");
        		 change.append("Original: <br>");
        		 for(int i=0; i<delta.getOriginal().getLines().size(); i++)
	        	 {
	        		 change.append(delta.getOriginal().getLines().get(i) + "<br>");
	        	 }
        		 change.append("Revised: <br>");
        		 for(int i=0; i<delta.getRevised().getLines().size(); i++)
	        	 {
	        		 change.append(delta.getRevised().getLines().get(i) + "<br>");
	        	 } 
        	 }
        	 
        	 change.append("</td></tr>");
         }
         
         change.append("</table>");
         
         //change.append("<script>$('#tblcodechange').DataTable();</script>");
         
         return change.toString();
	}
	
	private static int getColorRed(int rgb)
	{
		return (rgb >> 16) & 0xff;
	}
	
	private static int getColorGreen(int rgb)
	{
		return (rgb >> 8) & 0xff;
	}
	
	private static int getColorBlue(int rgb)
	{
		return rgb & 0xff;
	}
	
	public static double meanOfImage(BufferedImage img)
	{
		double sum = 0;
		for (int x = 0; x < img.getWidth(); x++) 
		{
            for (int y = 0; y < img.getHeight(); y++) 
            {
            	sum += img.getRGB(x, y);
            }
        }
		
		return sum * 1.0 / (img.getWidth() * img.getHeight());
	}
	
	public static double compareImage(BufferedImage img1, BufferedImage img2)
	{
		if (img1.getWidth() == img2.getWidth() && img1.getHeight() == img2.getHeight())
		{
			double m1 = meanOfImage(img1);
			double m2 = meanOfImage(img2);
			double sum = 0;
			double sum1 = 0;
			double sum2 = 0;
			for (int x = 0; x < img1.getWidth(); x++) 
			{
	            for (int y = 0; y < img1.getHeight(); y++) 
	            {
	            	sum += (img1.getRGB(x, y) - m1) * (img2.getRGB(x, y) - m2);
	            	sum1 += (img1.getRGB(x, y) - m1) * (img1.getRGB(x, y) - m1);
	            	sum2 += (img2.getRGB(x, y) - m2) * (img2.getRGB(x, y) - m2);
	            }
	        }
			
			return sum / (Math.sqrt(sum1) * Math.sqrt(sum2));
		}
		return 0;
	}
	
	public static void main(String[] args) throws Exception
	{
		Logger logger = Logger.getLogger(CompareUtil.class.getName());
		
		try
		{
			throw new Exception("ssss");
		}catch(Exception e)
		{
			logger.info(e.toString());
		}
		
		
		//DBImpl db = new DBImpl();
		
		//LowLevelInteraction i1 = db.getAnInteractions("2015-02-06 09:36:05.333", "baolingfeng");
		//LowLevelInteraction i2 = db.getAnInteractions("2015-02-06 09:17:22.408", "baolingfeng");
		//System.out.println(CompareUtil.compareText(i2.getUiValue(), i1.getUiValue()));
		//BufferedImage img1 = ImageIO.read(new File("C:\\Users\\baolingfeng\\Desktop\\Collecter\\log\\screen\\2015-02-08-20-06-49-624.png"));
		//BufferedImage img2 = ImageIO.read(new File("C:\\Users\\baolingfeng\\Desktop\\Collecter\\log\\screen\\2015-02-08-20-08-12-449.png"));
		
		//System.out.println(CompareUtil.compareImage(img1, img2));
	}
}
