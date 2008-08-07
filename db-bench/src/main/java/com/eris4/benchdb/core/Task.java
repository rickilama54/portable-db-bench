package com.eris4.benchdb.core;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.eris4.benchdb.core.monitor.AvgTransactionMonitor;
import com.eris4.benchdb.core.monitor.Monitor;
import com.eris4.benchdb.core.monitor.TimeMonitor;
import com.eris4.benchdb.core.util.ThreadUtils;

public class Task implements Runnable{

	private boolean stop;
	private List<Operation> operations;
	private List<Monitor> monitors;
	private int transactionPerSecond;
	private int transactionCheckTime = 10;
	private Logger logger = Logger.getLogger(Task.class);
		
	public Task(List<Operation> operations){
		this.operations = operations;
		monitors = new LinkedList<Monitor>();
	}
	
	public void setDatabase(Database database) throws NoSuitableDriverException {
		for (Operation operation : operations) {
			operation.setDatabase(database);
		}
	}
	
	public void setUp() throws TestDriverException, OperationException{
		for (Monitor monitor : monitors) {
			monitor.reset();
		}
		for (Operation operation : operations) {
			operation.setUp();
		}
	}

	public void warmUp() throws OperationException, TestDriverException {
		for (Monitor monitor : monitors) {
			monitor.warmUp();
		}		
		for (Operation operation : operations) {
			operation.warmUp();
		}
	}	

	@Override
	public void run() {
		logger.trace("Task started");
		stop = false;
		AvgTransactionMonitor transactionMonitor = new AvgTransactionMonitor();
		int sleepTime = 1000/(transactionPerSecond/transactionCheckTime);
		transactionMonitor.start();
		TimeMonitor timeKeeper = new TimeMonitor();
		for (Monitor monitor : monitors) {
			monitor.start();
		}	
		logger.trace("Starting while cicle");
		synchronized (operations) {
			while (!stop) {
				timeKeeper.start();
				for (int i = 0; i <= transactionCheckTime  && !stop; i++) {
					for (Operation operation : operations) {
						try {
							operation.execute();
						} catch (Exception e) {
							logger.error("Task stopped: an error during the execution of an operation ", e);	
							stop();
							break;
						} 
					}
					for (Monitor monitor : monitors) {
						monitor.update();
					}
					transactionMonitor.update();
				}
				if (transactionMonitor.getValue() > transactionPerSecond){
//					long sleepTime = MyMath.getMillisToNextSecond(timeKeeper.getValue());
					ThreadUtils.sleep(sleepTime);
				}
			}
		}
		logger.trace("Out of the while");
		transactionMonitor.stop();
		for (Monitor monitor : monitors) {
			monitor.stop();
		}
		
	}
	
	public void stop(){
		stop = true;
	}

	public void tearDown() throws OperationException, TestDriverException {
		synchronized (operations) {
			for (Operation operation : operations) {
				operation.tearDown();
			}
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
