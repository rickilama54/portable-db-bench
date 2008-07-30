package com.eris4.benchdb.core;

import java.util.List;

import com.eris4.benchdb.core.reporter.Reporter;


public class TestRunner {

	public void execute(List<Test> tests,List<Database> databases, List<Reporter> reporters) {
		for (Test test : tests) {
			execute(test,databases,reporters);
		}
	}

	public void execute(Test test,List<Database> databases,List<Reporter> reporters) {
		for (Database database : databases) {
			execute(test,database,reporters);
		}
	}

	public void execute(Test test,Database database,List<Reporter> reporters) {
		try {
			database.clear();
			test.setDatabase(database);
			for (Reporter reporter : reporters) {
				reporter.setDatabase(database);
			}
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
