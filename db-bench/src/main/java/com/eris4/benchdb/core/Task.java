package com.eris4.benchdb.core;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.eris4.benchdb.core.monitor.AvgTransactionMonitor;
import com.eris4.benchdb.core.monitor.Monitor;
import com.eris4.benchdb.core.monitor.TimeMonitor;
import com.eris4.benchdb.core.monitor.TotalTransactionMonitor;
import com.eris4.benchdb.core.monitor.TransactionMonitor;
import com.eris4.benchdb.core.util.ThreadUtils;

public class Task implements Runnable{

	private boolean stop;
	private int totalTransaction = Integer.MAX_VALUE;
	private int transactionCheckTime = 5;
	private int transactionPerSecond = Integer.MAX_VALUE;
	private long time;
	
	private AvgTransactionMonitor avgTransactionMonitor;
	private TotalTransactionMonitor totalTransactionMonitor;
	private TransactionMonitor transactionMonitor;
	private TimeMonitor timeMonitor;

	private List<Operation> operations;
	private List<Monitor> monitors;		
	private Logger logger = Logger.getLogger(Task.class);
	private String name;
		
	public Task(List<Operation> operations){
		this.operations = operations;
		this.monitors = new LinkedList<Monitor>();
		avgTransactionMonitor = new AvgTransactionMonitor();
		totalTransactionMonitor = new TotalTransactionMonitor();
		timeMonitor = new TimeMonitor();
		transactionMonitor = new TransactionMonitor();
		this.monitors.add(transactionMonitor);
		this.monitors.add(avgTransactionMonitor);
		this.monitors.add(totalTransactionMonitor);
		this.monitors.add(timeMonitor);
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
		stop = false;		
		
	}

	public void warmUp() throws OperationException, TestDriverException {
		for (Monitor monitor : monitors) {
			monitor.warmUp();
		}		
		for (Operation operation : operations) {
			operation.warmUp();
		}
	}	

	public void run() {
		logger.trace("Task started");		
		int sleepTime = 1500/(transactionPerSecond/transactionCheckTime);	
		transactionMonitor.setPeriodCheckTime(Math.max(sleepTime*3, 100));//ahahah
		for (Monitor monitor : monitors) {
			monitor.start();
		}	
		logger.trace("Starting while cicle");
		synchronized (operations) {
			while (!isStopped()) {				
				for (int i = 0; i <= transactionCheckTime  && !isStopped(); i++) {
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
				}
				if (avgTransactionMonitor.getValue() > transactionPerSecond){
//					long sleepTime = MyMath.getMillisToNextSecond(timeKeeper.getValue());
					ThreadUtils.sleep(sleepTime);
				}
			}
		}
		logger.trace("Out of the while");
		for (Monitor monitor : monitors) {
			monitor.stop();
		}
		synchronized (this) {
			notify();
		}
		
		
	}	
	
	public boolean isStopped() {
		if (stop || (totalTransactionMonitor.getValue() >= totalTransaction) || timeMonitor.getValue() > time){
			return true;
		}
		return false;
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
		this.transactionCheckTime = transactionPerSecond / 100;
		if (this.transactionCheckTime < 1){
			this.transactionCheckTime = 2;
		}
	}
	
	public void setTotalTransaction(int totalTransaction) {
		this.totalTransaction = totalTransaction;
	}
	
	public void setTime(long time) {
		this.time = time;
	}
	
	public long getRemainingTime(){
		return time - timeMonitor.getValue();
	}

	public void setMonitors(Collection<Monitor> monitors) {		
		for (Monitor monitor : monitors) {
			if (monitor instanceof AvgTransactionMonitor) {
				this.monitors.remove(avgTransactionMonitor);
				avgTransactionMonitor = (AvgTransactionMonitor) monitor;				
			} else if (monitor instanceof TotalTransactionMonitor) {
				this.monitors.remove(totalTransactionMonitor);
				totalTransactionMonitor = (TotalTransactionMonitor) monitor;				
			} else if (monitor instanceof TimeMonitor) {
				this.monitors.remove(timeMonitor);
				timeMonitor = (TimeMonitor) monitor;				
			} else if (monitor instanceof TransactionMonitor) {
				this.monitors.remove(transactionMonitor);
				transactionMonitor = (TransactionMonitor) monitor;				
			}
			this.monitors.add(monitor);			
		}
	}

	public void printResult() {
		System.out.println("============== task result ============");
		for (Monitor monitor: monitors) {
			System.out.println(monitor.getDescription()+" -- "+monitor.getFormattedValue());
		}
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	
}
