package cn.zju.blf.video.dao;

import java.util.Comparator;

public class EventComparator implements Comparator<LowLevelEvent>{
	
	public int compare(LowLevelEvent e1, LowLevelEvent e2) {
		return e1.getTimestamp().compareTo(e2.getTimestamp());
	}
}
