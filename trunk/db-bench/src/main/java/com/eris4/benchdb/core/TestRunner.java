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
		try {
			database.clear();
			test.setDatabase(database);			
			test.start();
		} catch (Exception e) {
			System.err.println(test.getName()+" is skipped on database: "+database.getClass().getSimpleName());
			e.printStackTrace();
		} 
//		catch (NoSuitableDriverException e) {
//			System.err.println(test.getName()+" is skipped on database: "+database.getClass().getSimpleName());
//			System.err.println(e);
//			e.printStackTrace();
//		} catch (TestDriverException e) {
//			System.err.println(test.getName()+" is skipped on database: "+database.getClass().getSimpleName());
//			System.err.println(e);
//			e.printStackTrace();
//		} catch (OperationException e) {
//			System.err.println(test.getName()+" is skipped on database: "+database.getClass().getSimpleName());
//			System.err.println(e);
//			e.printStackTrace();
//		}
	}

}
