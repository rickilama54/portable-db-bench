package com.eris4.benchdb.core;

import java.util.List;

import org.apache.log4j.Logger;


public class TestRunner {
	
	private Logger logger = Logger.getLogger(TestRunner.class);

	public void execute(List<Test> tests,List<Database> databases) {
		for (Test test : tests) {
			execute(test,databases);
		}
	}

	public void execute(Test test,List<Database> databases) {
		for (Database database : databases) {
			execute(test,database);
		}
	}

	public void execute(Test test,Database database) {
		try {
			database.clear();
			test.setDatabase(database);			
			test.start();
		} catch (Exception e) {
			logger.error(test.getName()+" is skipped on database: "+database.getClass().getSimpleName(), e);			
		} 
	}

}
