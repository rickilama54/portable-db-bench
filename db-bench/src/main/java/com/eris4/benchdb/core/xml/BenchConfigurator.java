package com.eris4.benchdb.core.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.eris4.benchdb.core.Database;
import com.eris4.benchdb.core.Test;
import com.eris4.benchdb.core.util.Resource;

public class BenchConfigurator {


	private DatabaseConfigurator databaseConfigurator;
	private TestsConfigurator testConfigurator;
	private ReportersConfigurator reporterConfigurator;
	private DefinitionsConfigurator definitionsConfigurator;	
	private Logger logger = Logger.getLogger(BenchConfigurator.class);

	public BenchConfigurator(String fileName) throws FileNotFoundException, ParserXmlException, URISyntaxException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		File file = Resource.getResourceFile(fileName);
		logger.debug(file.getAbsolutePath());
		Document document = new ParserXml().parseXml(file);//		
		NodeList definitions = document.getElementsByTagName(XmlConstants.DEFINITIONS_NODE).item(0).getChildNodes();
		NodeList databases = document.getElementsByTagName(XmlConstants.DATABASE_NODE);
		NodeList tests = document.getElementsByTagName(XmlConstants.TEST_NODE);

		databaseConfigurator = new DatabaseConfigurator(databases);
		definitionsConfigurator = new DefinitionsConfigurator(definitions);
		testConfigurator = new TestsConfigurator(tests,definitionsConfigurator);
	}


	public List<Test> readTests() throws InstantiationException, IllegalAccessException, ClassNotFoundException, MissingDefinitionException {
		return testConfigurator.readTests();
	}

	public List<Database> readDatabases() throws ClassNotFoundException, InstantiationException, IllegalAccessException  {
		return databaseConfigurator.readDatabases();
	}

}
