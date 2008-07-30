package com.eris4.benchdb;

import java.util.List;

import com.eris4.benchdb.core.Database;
import com.eris4.benchdb.core.Test;
import com.eris4.benchdb.core.TestRunner;
import com.eris4.benchdb.core.xml.BenchConfigurator;

public class MainFromXml {

	private static final String FILE_NAME = "bench-properties.xml";

	public static void main(String[] args) throws Exception {
		System.out.println("Loading xml ...");
		BenchConfigurator configurator = new BenchConfigurator(FILE_NAME);
		List<Database> databases = configurator.readDatabases();
		List<Test> tests = configurator.readTests();			
		System.out.println("Number of tests: "+tests.size());
		System.out.println("Number of databases: "+databases.size());
		new TestRunner().execute(tests,databases);		
	}
	
}
