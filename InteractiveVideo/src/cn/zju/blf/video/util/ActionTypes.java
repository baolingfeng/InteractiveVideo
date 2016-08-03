package cn.zju.blf.video.util;

import java.util.HashMap;
import java.util.Map;

public class ActionTypes {
	public static Map<String, String> UIs = new HashMap<String, String>();
	
	static {
		UIs.put("tab", "选项卡");
		UIs.put("tree", "树");
		UIs.put("window", "窗口");
		UIs.put("pane", "窗格");
		UIs.put("dialog", "对话框");
		UIs.put("edit", "编辑");
		UIs.put("tree item", "树项目");
		UIs.put("menu", "菜单");
		UIs.put("menu item", "菜单项目");
		UIs.put("list", "列表");
		UIs.put("list item", "列表项目");
		UIs.put("text", "文本");
		UIs.put("scroll bar", "滚动条");
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
