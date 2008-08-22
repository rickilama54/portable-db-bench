package com.eris4.benchdb.core;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.eris4.benchdb.core.reporter.Reporter;
import com.eris4.benchdb.core.util.ThreadUtils;
import com.lowagie.text.Chapter;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Section;

public class Test {

	private static Logger logger = Logger.getLogger(Test.class);
	private Database database;
	private List<Task> tasks;
	private List<DbInitializator> dbInitializators;
	private List<Reporter> reporters;	
	private long time;
	private String name;
	
	public void start() throws NoSuitableDriverException, OperationException, TestDriverException{
		System.out.println("########### STARTING TEST ############");
		System.out.println("########### "+name+" ############");
		System.out.println("########### "+database.getClass().getSimpleName()+" ############");
		List<Thread> threads = new LinkedList<Thread>();
		try {
			logger.info("DB INITIALIZATION...");
			for (DbInitializator dbInitializator : dbInitializators) {
				dbInitializator.init(database);
			}
			logger.info("THREAD CREATION...");
			for (Task task : tasks) {
				threads.add(new Thread(task));
			}		
			logger.info("TASK SET UP...");
			for (Task task : tasks) {
				task.setUp();
			}
			logger.info("TASK WARMP UP...");
			for (Task task : tasks) {
				task.warmUp();
			}
			for (Reporter reporter: reporters) {
				reporter.start();
			}
			logger.info("RUNNING TEST...");
			for (Thread thread: threads) {
				thread.start();
			}
			ThreadUtils.sleep(time); //a better timer can be used, but there is no need for a perfect one
			logger.info("STOPPPING TEST...");
			for (Reporter reporter: reporters) {
				reporter.stop();
			}
			for (Task task : tasks) {
				task.stop();
			}
		} catch (OperationException e) {
			throw e;
		} catch (TestDriverException e) {
			throw e;
		} finally {
			try {
				logger.info("TASK TEAR DOWN...");
				for (Task task : tasks) {
					task.tearDown();
				}				
				logger.info("TEST FINISHED!");
			} catch (Exception e) {
				logger.warn("Can't tear down task "+e.getMessage());
			}
		}		
		for (Task task : tasks) {
			task.printResult();
		}
		
	}
	
	public Test(List<DbInitializator> dbInitializators,List<Task> tasks,List<Reporter> reporters,long time, String name){
		this.dbInitializators = dbInitializators;
		this.tasks = tasks;
		this.reporters = reporters;
		this.time = time;
		this.name = name;
		for (DbInitializator dbInitializator : dbInitializators) {
			for (Reporter reporter: this.reporters) {
				reporter.addDescription(dbInitializator.getDescription());
			}
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
	}
	
	public String getName() {
		return name;
	}

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
