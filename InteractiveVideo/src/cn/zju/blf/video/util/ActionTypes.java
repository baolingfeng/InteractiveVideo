package cn.zju.blf.video.util;

import java.util.HashMap;
import java.util.Map;

public class ActionTypes {
	public static Map<String, String> UIs = new HashMap<String, String>();
	
	static {
		UIs.put("tab", "ѡ�");
		UIs.put("tree", "��");
		UIs.put("window", "����");
		UIs.put("pane", "����");
		UIs.put("dialog", "�Ի���");
		UIs.put("edit", "�༭");
		UIs.put("tree item", "����Ŀ");
		UIs.put("menu", "�˵�");
		UIs.put("menu item", "�˵���Ŀ");
		UIs.put("list", "�б�");
		UIs.put("list item", "�б���Ŀ");
		UIs.put("text", "�ı�");
		UIs.put("scroll bar", "������");
	}
	
	public static boolean isEqual(String engUI, String target)
	{
		if(UIs.containsKey(engUI))
		{
			return engUI.equals(target) || UIs.get(engUI).equals(target);
		}
		else
		{
			return engUI.equals(target);
		}
	}
 }
