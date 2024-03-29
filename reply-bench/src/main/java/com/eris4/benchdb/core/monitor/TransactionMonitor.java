package com.eris4.benchdb.core.monitor;

import com.eris4.benchdb.core.util.ThreadUtils;

public class TransactionMonitor extends Monitor{

	private static final int HISTORY_LENGTH = 2;
	private long numberOfTransaction;
	private long[] savedNumberOfTransction = new long[HISTORY_LENGTH];
	private TimeMonitor timeMonitor = new TimeMonitor();
	private boolean stop;
	private long[] savedTime = new long[HISTORY_LENGTH];
	private long PERIOD_CHECK = 100;
	private long savedValue;
	

	
	public String getDescription() {
		return "Transaction per second";
	}
	
	
	public void begin() {
		stop = false;
		numberOfTransaction = 0;
		savedNumberOfTransction = new long[HISTORY_LENGTH];
		savedTime = new long[HISTORY_LENGTH];
		new Thread(){
			public void run(){
				while(!stop){
					ThreadUtils.sleep(PERIOD_CHECK);
					for (int i = 0; i < HISTORY_LENGTH-1; i++) {
						savedNumberOfTransction[i] = savedNumberOfTransction[i+1];
						savedTime[i] = savedTime[i+1];
					}
					savedNumberOfTransction[HISTORY_LENGTH-1] = numberOfTransaction;
					savedTime[HISTORY_LENGTH-1] = timeMonitor.getValue();
					//TODO gestire accessi concorrenti alle 2 variabili
				}
			}
		}.start();
		timeMonitor.start();
	}
	
	
	public void end(){
		timeMonitor.stop();
		savedValue = getValue();
		stop = true;		
	}

	
	public long getValue() {		
		if(stop){
			return savedValue;
		}
		long time = timeMonitor.getValue() - savedTime[0];
		if (time <= 0){
			time = 1;
		}
		return ((numberOfTransaction - savedNumberOfTransction[0]) * 1000) / time;
	}
	
	public long getAvgValue() {
		long time = timeMonitor.getValue();
		if (time == 0)
			time = 1;
		return (numberOfTransaction * 1000) / time ;
	}

	
	public void update() {
		numberOfTransaction++;
	}

	
	public void reset() {
		savedNumberOfTransction[0] = numberOfTransaction;
		savedValue = 0;
		numberOfTransaction = 0;
	}

	public void setPeriodCheckTime(int checkTime) {
		this.PERIOD_CHECK = checkTime;		
	}
	

}
