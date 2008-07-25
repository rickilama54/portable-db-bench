package com.eris4.benchdb.core;

import java.util.List;


public class TestRunner {

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
		database.clear();
		try {
			database.clear();
			test.setDatabase(database);
			test.start();
		} catch (NoSuitableDriverException e) {
			System.err.println(test.getClass().getSimpleName()+" is skipped");
			System.err.println(e);
		} catch (TestDriverException e) {
			System.err.println(test.getClass().getSimpleName()+" is skipped");
			System.err.println(e);
		}
		//TODO la close del database deve cmq essere effettuata!!!!!
	}

}
