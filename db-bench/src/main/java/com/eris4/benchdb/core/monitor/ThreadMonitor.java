package com.eris4.benchdb.core.monitor;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class ThreadMonitor {
	
	private Thread thread;
	private long startIntervalTime;
	private long startIntervalUserTime;
	private ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
	private long id;
	private long startTime;
	private long startUserTime;
		
	public ThreadMonitor(Thread thread){
		this.thread = thread;		
		this.id = thread.getId();
		startTime = startIntervalTime = System.nanoTime();
		startUserTime = startIntervalUserTime = threadMXBean.getThreadUserTime(id);
	}	
	public ThreadMonitor(long id){
			
		this.id = id;
		startTime = startIntervalTime = System.nanoTime();
		startUserTime = startIntervalUserTime = threadMXBean.getThreadUserTime(id);
	}
	
	public int getAvgValue(){
		int result = 0;
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
		return result;		
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
		
		long usedTime = tmpTime - startIntervalTime;
		long usedUserTime = tmpUserTime - startIntervalUserTime;
		if (usedTime >0){			
			result = (int)((usedUserTime*100)/usedTime);
		}
		if(result<0){
			result = 0;
		}
		startIntervalTime = tmpSavedTime;
		startIntervalUserTime = tmpUserTime;
		return result;
	}
	
}
