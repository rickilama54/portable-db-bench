package com.eris4.benchdb.core;

import java.io.PrintStream;

import com.eris4.benchdb.core.monitor.Monitors;
import com.eris4.benchdb.core.monitor.TimeMonitor;

public abstract class OldTest implements Runnable{
	
	private Database database;
	private PrintStream out = System.out;
	
	public final void start() throws NoSuitableDriverException, TestDriverException{
		System.out.println();
		System.out.println("====== Test: "+this.getClass().getSimpleName()+"  -  Database: "+database.getClass().getSimpleName()+" ======");
		System.out.println(getDescription());
		System.out.println();
		TimeMonitor timeMonitor = new TimeMonitor();
				
		timeMonitor.start();		
		setUp();		
		System.out.println("> Initialization done in "+timeMonitor.getFormattedValue());
		
		timeMonitor.start();
		warmUp();		
		System.out.println("> Warm up done in "+timeMonitor.getFormattedValue());
		
		timeMonitor.start();
		run();
		System.out.println("> Run done in "+timeMonitor.getFormattedValue());
		
		timeMonitor.start();
		tearDown();
		System.out.println("> Tear down done in "+timeMonitor.getFormattedValue());
	}
		
	public void setPrintStrem(PrintStream printStream){
		this.out = printStream;
	}
	
	public PrintStream getPrintStrem() {
		return out;
	}
	
	public void setDatabase(Database database) {
		this.database = database;
	}	
	
	public Database getDatabase() {
		return database;
	}		
	
	public abstract void setUp() throws NoSuitableDriverException, TestDriverException;
	public abstract void warmUp();
	public abstract void run();
	public abstract void stop();
	public abstract void tearDown();	
	
	public abstract void setMonitors(Monitors monitors);
	public abstract String getDescription();	
	public abstract void printResult();
	
}