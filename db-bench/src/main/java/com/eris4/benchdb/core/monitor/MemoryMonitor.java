
package com.eris4.benchdb.core.monitor;

import com.eris4.benchdb.core.util.ThreadUtils;

public class MemoryMonitor extends Monitor{

	private static final int PERIOD_CHECK = 50;
	private static final int NUMBER_GARBAGE_COLLECTOR_INVOCATION = 10;	
	private long startMemory;	
	private long maxMemory;	
	private boolean stop;
	
	@Override
	public String getDescription() {
		return "Memory usage (bytes)";
	}
	
	@Override
	public void warmUp() {
		resetMemory();
	}
	
	@Override
	public void begin() {
		stop = false;		
		maxMemory = startMemory = getUsedMemory();		
		new Thread(){
			public void run() {
				while(!stop) {
					monitorMemory();
					ThreadUtils.sleep(PERIOD_CHECK);
				}
			}			
		}.start();
	}	

	@Override
	public void end() {
		stop = true;
		monitorMemory();
	}

	@Override
	public long getValue() {
		return maxMemory - startMemory ;
	}
	
	
	
	private static long getUsedMemory(){
		Runtime runtime = Runtime.getRuntime();
		return runtime.totalMemory() - runtime.freeMemory();
	}
	
	private void monitorMemory() {
		long usedMemory = getUsedMemory();
		if(usedMemory > maxMemory) {
			maxMemory = usedMemory;
		}
	}
	
	protected static void resetMemory() {
		for (int i = 0; i < NUMBER_GARBAGE_COLLECTOR_INVOCATION; ++i) {
			System.gc();
			System.runFinalization();
		}
		try {
			Thread.sleep(PERIOD_CHECK);
		} catch (InterruptedException e) {
			//do nothing
		}
	}

}