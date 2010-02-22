package com.eris4.benchdb.core;

import java.io.PrintStream;

public class TestResult {
	
	private long time;
	private double transactionPerSecond;
	private long maxUsedMemory;

	public TestResult(long time,double transactionPerSecond,long maxUsedMemory){
		this.time = time;
		this.transactionPerSecond = transactionPerSecond;
		this.maxUsedMemory = maxUsedMemory;
	}
	
	public long getTime(){
		return time;
	}
	
	public double getTransactionPerSecond(){
		return transactionPerSecond;
	}
	public long getMaxUsedMemory(){
		return maxUsedMemory;
	}

	public void print(PrintStream out) {
//		out.println("========= Time, transaction, memory monitors result =========");
		out.print("Time: " + getTime());
		out.print(" Memory: " + getMaxUsedMemory());
		out.println(" Transaction Per Second: " + getTransactionPerSecond());
		
	}

}
