package com.eris4.benchdb.core.monitor;

public class AvgTransactionMonitor extends TransactionMonitor {
	
	@Override
	public long getValue() {
		return super.getAvgValue();
	}
	
	@Override
	public String getDescription() {
		return "Avg transaction per second";
	}

}
