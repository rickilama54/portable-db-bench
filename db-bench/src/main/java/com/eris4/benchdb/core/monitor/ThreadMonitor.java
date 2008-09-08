package com.eris4.benchdb.core.monitor;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class ThreadMonitor {
	
	private Thread thread;
	private long startTime;
	private long startUserTime;
	private ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
	private long id;
		
	public ThreadMonitor(Thread thread){
		this.thread = thread;		
		this.id = thread.getId();
		startTime = System.nanoTime();
		startUserTime = threadMXBean.getThreadUserTime(id);
	}	

/**
 * 
 * @return the % value
 */
	public int getValue(){
		int result = 0;
		long tmpSavedTime = System.nanoTime();
		long tmpUserTime = threadMXBean.getThreadUserTime(id);
		long tmpTime = System.nanoTime();
		
		long usedTime = tmpTime - startTime;
		long usedUserTime = tmpUserTime - startUserTime;
		if (usedTime >0){			
			result = (int)((usedUserTime*100)/usedTime);
		}
		if(result<0){
			result = 0;
		}
//		if(result>100){
//			result = 100;
//		}
		startTime = tmpSavedTime;
		startUserTime = tmpUserTime;
		return result;
	}
	
}
