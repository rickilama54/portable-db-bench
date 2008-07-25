package com.eris4.benchdb.core;

import com.eris4.benchdb.core.monitor.FileMonitor;
import com.eris4.benchdb.core.monitor.MemoryMonitor;
import com.eris4.benchdb.core.monitor.Monitors;
import com.eris4.benchdb.core.monitor.TimeMonitor;
import com.eris4.benchdb.core.monitor.TransactionMonitor;
import com.eris4.benchdb.core.monitor.XYGraphMonitor;
import com.eris4.benchdb.core.util.ThreadUtils;

public abstract class ConcurrentTest extends OldTest {
	
	private boolean stop;

	@Override
	public void setUp() throws NoSuitableDriverException,TestDriverException {
		for (BaseTest test : getTests()) {
			test.setDatabase(getDatabase());
		}	
		init();
	}
	
	@Override
	public void warmUp() {
		for (BaseTest test : getTests()) {
			test.warmUp();
		}
	}
	
	@Override
	public void tearDown() {
		for (BaseTest test : getTests()) {
			test.tearDown();
		}
	}

	@Override
	public void printResult() {
		for (BaseTest test : getTests()) {
			System.out.println();
			System.out.println(test.getClass().getSimpleName());
			test.setPrintStrem(getPrintStrem());//default System.out
			test.printResult();
		}		
	}	

	@Override
	public void run() {
		stop = false;
		for (BaseTest test : getTests()) {			
			new Thread(test).start();
		}	
		while(!stop){
			for (BaseTest test : getTests()) {				
				if (test.isStopped()){
					stop();
				}	
				ThreadUtils.sleep(20);
			}			
		}			
	}
	
	@Override
	public void stop() {
		stop = true;
		for (BaseTest test : getTests()) {			
			test.stop();
		}	
	}
	
	@Override
	public void setMonitors(Monitors monitors2) {		
		XYGraphMonitor graphMonitor = new XYGraphMonitor(this.getClass().getSimpleName()+"-"+getDatabase().getClass().getSimpleName());
		for (BaseTest test : getTests()) {			
			Monitors monitors = new Monitors();
			TimeMonitor timeMonitor = new TimeMonitor();
			TransactionMonitor transactionMonitor = new TransactionMonitor();
			graphMonitor.add(test.getClass().getSimpleName(), timeMonitor, transactionMonitor);
			monitors.add(timeMonitor);		
			monitors.add(transactionMonitor);
			monitors.add(new MemoryMonitor());
			monitors.add(new FileMonitor(getDatabase().getFileName()));
			monitors.add(graphMonitor);
			test.setMonitors(monitors);
		}
	}
	
	public abstract BaseTest[] getTests();
	public abstract void init() throws NoSuitableDriverException, TestDriverException ;
	
}
