package com.eris4.benchdb.core.monitor;

public class AvgTransactionMonitor extends TransactionMonitor {
	
	
	public long getValue() {
		return super.getAvgValue();
	}
	
	
	public String getDescription() {
		return "Avg transaction per second";
	}

}
