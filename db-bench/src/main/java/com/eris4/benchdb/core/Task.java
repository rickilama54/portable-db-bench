package com.eris4.benchdb.core;

import java.util.LinkedList;
import java.util.List;

import com.eris4.benchdb.core.monitor.Monitor;

public class Task implements Runnable{

	private boolean stop;
	private List<Operation> operations;
	private List<Monitor> monitors;
	private int transactionPerSecond;
	
	public Task(List<Operation> operations){
		this.operations = operations;
		monitors = new LinkedList<Monitor>();
	}
	
	public void setDatabase(Database database) throws NoSuitableDriverException {
		for (Operation operation : operations) {
			operation.setDatabase(database);
		}
	}
	
	public void setUp() throws TestDriverException{
		for (Operation operation : operations) {
			operation.setUp();
		}
	}

	public void warmUp() {
		for (Monitor monitor : monitors) {
			monitor.warmUp();
		}		
		for (Operation operation : operations) {
			operation.warmUp();
		}
	}	

	@Override
	public void run() {
		stop = false;
		for (Monitor monitor : monitors) {
			monitor.start();
		}
		while (!stop) {
			for (Operation operation : operations) {
				operation.execute();
			}
			for (Monitor monitor : monitors) {
				monitor.update();
			}
		}
		for (Monitor monitor : monitors) {
			monitor.stop();
		}
	}
	
	public void stop(){
		stop = true;
		//chiamare teardown direttamente qui?		
	}

	public void tearDown() {
		for (Operation operation : operations) {
			operation.tearDown();
		}		
	}

	public void setTransactionPerSecond(int transactionPerSecond) {
		this.transactionPerSecond  = transactionPerSecond;
	}

	public void setMonitors(List<Monitor> monitors) {
		this.monitors = monitors;
	}

	public void printResult() {
		System.out.println("============== task result ============");
		for (Monitor monitor: monitors) {
			System.out.println(monitor.getDescription()+" -- "+monitor.getFormattedValue());
		}
	}

}
