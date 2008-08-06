package com.eris4.benchdb.core.monitor;


public class TotalTransactionMonitor extends Monitor{

	private long numberOfTransaction;

	@Override
	public String getDescription() {
		return "Total number of transactions";
	}
	
	@Override
	public void begin() {		
		numberOfTransaction = 0;		
	}
	
	@Override
	public void end(){		
	}

	@Override
	public long getValue() {
		return numberOfTransaction;
	}		

	@Override
	public void update() {
		numberOfTransaction++;
	}

	@Override
	public void reset() {
		numberOfTransaction = 0;
	}
	

}
