package com.eris4.benchdb.core.monitor;

public class TimeMonitor extends Monitor {
	
	private long startTime;
	private long totalTime = 0;
	private boolean stop = true;
	
	@Override
	public String getDescription() {
		return "Time passed (sec)";
	}
	
	@Override
	public void begin(){
		startTime = System.currentTimeMillis();
		totalTime = 0;
		stop = false;
	}
	
	@Override
	public void end(){
		if (stop == false){
			totalTime += System.currentTimeMillis() - startTime;
		}
		stop = true;		
	}
	
	public void resume(){
		startTime = System.currentTimeMillis();
	}
	
	@Override
	public long getValue(){
		if (stop)
			return totalTime;
		return (System.currentTimeMillis() - startTime) + totalTime;
	}

	
}
