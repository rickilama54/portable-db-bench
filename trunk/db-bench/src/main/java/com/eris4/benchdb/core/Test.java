package com.eris4.benchdb.core;

import java.util.LinkedList;
import java.util.List;

import com.eris4.benchdb.core.reporter.Reporter;
import com.eris4.benchdb.core.util.ThreadUtils;

public class Test {

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
			System.out.println("DB INITIALIZATION...");
			for (DbInitializator dbInitializator : dbInitializators) {
				dbInitializator.init(database);
			}
			System.out.println("THREAD CREATION...");
			for (Task task : tasks) {
				threads.add(new Thread(task));
			}			
			System.out.println("TASK SET UP...");
			for (Task task : tasks) {
				task.setUp();
			}
			System.out.println("TASK WARMP UP...");
			for (Task task : tasks) {
				task.warmUp();
			}
			for (Reporter reporter: reporters) {
				reporter.start();
			}
			System.out.println("RUNNING TEST...");
			for (Thread thread: threads) {
				thread.start();
			}
			ThreadUtils.sleep(time); //a better timer can be used, but there is no need for a perfect one
			System.out.println("STOPPPING TEST...");
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
				System.out.println("TASK TEAR DOWN...");
				for (Task task : tasks) {
					task.tearDown();
				}
				System.out.println("TEST FINISHED!");
			} catch (RuntimeException e) {
				System.out.println("Task tear down error: ");
				e.printStackTrace();
			}
		}		
		for (Task task : tasks) {
			task.printResult();
		}
		for (Reporter reporter: reporters) {
			reporter.report();
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
	
}
