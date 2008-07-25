package com.eris4.benchdb.core.xml;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.eris4.benchdb.core.Database;
import com.eris4.benchdb.core.Test;
import com.eris4.benchdb.core.util.Resource;

public class BenchConfigurator {

	private static final String TESTS_NODE_NAME = "tests";
	private static final String DATABASES_NODE_NAME = "databases";
	private static final String DEFINITIONS_NODE_NAME = "definitions";
	private DatabaseConfigurator databaseConfigurator;
	private TestsConfigurator testConfigurator;	

	public BenchConfigurator(String fileName) throws FileNotFoundException, ParserXmlException, URISyntaxException {
		Document document = new ParserXml().parseXml(Resource.getResourceFile(fileName));		
		NodeList definitions = document.getElementsByTagName(DEFINITIONS_NODE_NAME).item(0).getChildNodes();
		NodeList databases = document.getElementsByTagName(DATABASES_NODE_NAME).item(0).getChildNodes();
		NodeList tests = document.getElementsByTagName(TESTS_NODE_NAME).item(0).getChildNodes();
		databaseConfigurator = new DatabaseConfigurator(databases);
		testConfigurator = new TestsConfigurator(tests,definitions);
	}


	public List<Test> readTests() throws InstantiationException, IllegalAccessException, ClassNotFoundException, MissingDefinitionException {
		return testConfigurator.readTests();
	}

	public List<Database> readDatabases() throws ClassNotFoundException, InstantiationException, IllegalAccessException  {
		return databaseConfigurator.readDatabases();
	}

}
