package com.eris4.benchdb.core.monitor;


public class TotalTransactionMonitor extends Monitor{

	private long numberOfTransaction;

	
	public String getDescription() {
		return "Total number of transactions";
	}
	
	
	public void begin() {		
		numberOfTransaction = 0;		
	}
	
	
	public void end(){		
	}

	
	public long getValue() {
		return numberOfTransaction;
	}		

	
	public void update() {
		numberOfTransaction++;
	}

	
	public void reset() {
		numberOfTransaction = 0;
	}
	

}
