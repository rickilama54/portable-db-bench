package com.eris4.benchdb.core;

import com.eris4.benchdb.core.monitor.Monitors;

public abstract class BaseTest extends OldTest{
	
	private Monitors monitors;
	private boolean stop = false;
	
	
	@Override
	public void setDatabase(Database database) {
		super.setDatabase(database);
//		monitors = new Monitors();
//		TimeMonitor timeMonitor = new TimeMonitor();
//		TransactionMonitor transactionMonitor = new TransactionMonitor();
//		monitors.add(new XYGraphMonitor(getClass().getSimpleName()+" - "+database.getClass().getSimpleName(),timeMonitor,transactionMonitor));
//		monitors.add(timeMonitor);		
//		monitors.add(transactionMonitor);
//		monitors.add(new MemoryMonitor());
//		monitors.add(new FileMonitor(database.getFileName()));		
	}
	
	public void setMonitors(Monitors monitors) {
		this.monitors = monitors;
	}	
	
	@Override
	public void printResult() {
		// the out buffer should be a parameter (properties)
		monitors.printValue(getPrintStrem());		
	}	

	@Override
	public void run(){			
		stop = false;
		monitors.warmUp();
		monitors.start();
		executeTask();
		while(!stop && !isStopped()){
			executeTask();
			monitors.update();	
		}	
		monitors.stop();
	}
	
	@Override
	public void setUp() throws NoSuitableDriverException ,TestDriverException {
		connect();		
		init();
	}
	
	@Override
	public void tearDown() {
		close();
	}
	
	@Override
	public void stop(){
		stop = true;
	}
	
	public abstract void connect() throws TestDriverException, NoSuitableDriverException;
	public abstract boolean isStopped();
	public abstract void executeTask();
	public abstract void init() throws NoSuitableDriverException, TestDriverException;
	public abstract void close();

}
