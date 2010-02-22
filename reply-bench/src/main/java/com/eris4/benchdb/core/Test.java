package com.eris4.benchdb.core;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.eris4.benchdb.core.monitor.AvgTransactionMonitor;
import com.eris4.benchdb.core.monitor.CpuMonitor;
import com.eris4.benchdb.core.monitor.FileMonitor;
import com.eris4.benchdb.core.monitor.MemoryMonitor;
import com.eris4.benchdb.core.monitor.Monitor;
import com.eris4.benchdb.core.monitor.TimeMonitor;
import com.eris4.benchdb.core.monitor.TotalTransactionMonitor;
import com.eris4.benchdb.core.reporter.Reporter;
import com.lowagie.text.Chapter;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Section;

public class Test {

	private static Logger logger = Logger.getLogger(Test.class);
	private Database database;
	private String name;
	private Collection<Task> tasks;
	private List<DbInitializator> dbInitializators;
	private List<Reporter> reporters;	
	private List<Monitor> monitors;
	private FileMonitor fileMonitor;		
	private CpuMonitor cpuMonitor;
	
	public void start() throws NoSuitableDriverException, OperationException, TestDriverException, InterruptedException{
		logger.info("########### Test summary");
		logger.info("########### "+name);
		logger.info("########### "+database.getClass().getSimpleName());			
		try {
			List<Thread> threads = initializationTask();
			initializationMonitor(threads);			
			startTask(threads);			
			stopTask();
		} catch (OperationException e) {
			throw e;
		} catch (TestDriverException e) {
			throw e;
		} finally {
			tearDownTask();
		}		
		System.out.println("============== test result ============");
		for (Monitor monitor: monitors) {
			System.out.println(monitor.getDescription()+" -- "+monitor.getFormattedValue());
		}
		for (Task task : tasks) {
			task.printResult();
		}
		
	}

	private void tearDownTask() {
		try {
			logger.debug("Task tear down...");
			for (Task task : tasks) {
				task.tearDown();
			}				
			logger.info("Test finished correctly!");
		} catch (Exception e) {
			logger.warn("Can't tear down task "+e.getMessage());
		}
	}

	private void stopTask() throws InterruptedException {
		for (Task task : tasks) {
			synchronized (task) {
				if (!task.isStopped()){
					task.wait();
				}
			}				
		}		
		logger.info("Tasks stopped");
		for (Monitor monitor : monitors) {
			monitor.stop();
		}
		for (Reporter reporter: reporters) {
			reporter.stop();
		}
	}

	private void startTask(List<Thread> threads) {
		logger.info("Starting tasks...");
		for (Reporter reporter: reporters) {
			reporter.start();
		}
		for (Monitor monitor : monitors) {
			monitor.start();
		}
		for (Thread thread: threads) {
			thread.start();
		}
	}

	private void initializationMonitor(List<Thread> threads) {
		for (Monitor monitor : monitors) {
			monitor.reset();
		}
		for (Thread thread : threads) {
			cpuMonitor.addThread(thread);
		}
		fileMonitor.setFileName(database.getFileName());
		for (Monitor monitor : monitors) {
			monitor.warmUp();
		}		
	}

	private List<Thread> initializationTask() throws NoSuitableDriverException,	TestDriverException, OperationException {
		List<Thread> threads = new LinkedList<Thread>();	
		logger.info("Database initialization...");
		for (DbInitializator dbInitializator : dbInitializators) {
			dbInitializator.init(database);
		}			
		logger.debug("Thread creation...");			
		for (Task task : tasks) {
			threads.add(new Thread(task));
		}					
		logger.debug("Task set up...");
		for (Task task : tasks) {
			task.setUp();
		}
		logger.info("Task warm up...");
		for (Task task : tasks) {
			task.warmUp();
		}
		return threads;
	}
	
	public Test(List<DbInitializator> dbInitializators,Collection<Task> collection,List<Reporter> reporters,String name){
		monitors = new LinkedList<Monitor>();
		fileMonitor = new FileMonitor();
		cpuMonitor = new CpuMonitor();
		monitors.add(fileMonitor);		
		monitors.add(cpuMonitor);		
		
		this.dbInitializators = dbInitializators;
		this.tasks = collection;
		this.reporters = reporters;
		this.name = name;
		for (DbInitializator dbInitializator : dbInitializators) {
			for (Reporter reporter: this.reporters) {
				reporter.addDescription(dbInitializator.getDescription());
			}
		}		
	}	

	public void setMonitors(Collection<Monitor> monitors) {
		for (Monitor monitor : monitors) {
			if (monitor instanceof FileMonitor) {
				this.monitors.remove(fileMonitor);
				fileMonitor = (FileMonitor) monitor;				
			} else if (monitor instanceof CpuMonitor) {
				this.monitors.remove(cpuMonitor);
				cpuMonitor = (CpuMonitor) monitor;				
			} 
			this.monitors.add(monitor);			
		}
	}

	public void setDatabase(Database database) throws NoSuitableDriverException {
		this.database = database;
		for (Task task : tasks) {
			task.setDatabase(database);			
		}
		for (Reporter reporter: reporters) {
			reporter.setDatabase(database);
		}
//		fileMonitor.setFileName(database.getFileName());
	}
	
	public String getName() {
		return name;
	}
	
	
	/*
	 * print method
	 */	
	
	public void print(Chapter chapter) throws DocumentException {
		Paragraph sectionInitializator = new Paragraph("The Database Initialization",Printer.SECTION_FONT);
		sectionInitializator.setSpacingBefore(Printer.PARAGRAPH_SPACE_BEFORE);
		Section section1 = chapter.addSection(sectionInitializator);
		Paragraph introInitializator = new Paragraph("The Databases have been initialized with the following parameters:");
		introInitializator.setSpacingBefore(Printer.PARAGRAPH_SPACE_BEFORE);		
		com.lowagie.text.List parameters = new com.lowagie.text.List(false,Printer.LIST_LEADING);
		for (DbInitializator dbInitializator : dbInitializators) {
			parameters.add(dbInitializator.getDescription());
		}		
		section1.add(introInitializator);
		section1.add(parameters);
		
		
		for (Reporter reporter: reporters) {
			Paragraph reporterParagraph = new Paragraph(reporter.getName(),Printer.SECTION_FONT);
			reporterParagraph.setSpacingBefore(Printer.PARAGRAPH_SPACE_BEFORE);
			Section section = chapter.addSection(reporterParagraph);
			reporter.report(section);
		}	
	}

	
}
