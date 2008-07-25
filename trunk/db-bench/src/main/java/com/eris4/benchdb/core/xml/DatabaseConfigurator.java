package com.eris4.benchdb.core.xml;

import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.eris4.benchdb.core.Database;

public class DatabaseConfigurator {
	
	
	private static final String CLASS_ATTRIBUTE_NAME = "class";
	private static final String DATABASE_NODE_NAME = "database";
	private ClassLoader classLoader = ClassLoader.getSystemClassLoader();
	private NodeList databases;

	public DatabaseConfigurator(NodeList databases) {
		this.databases = databases;
	}
	
	public List<Database> readDatabases() throws ClassNotFoundException, InstantiationException, IllegalAccessException  {
		List<Database> result = new LinkedList<Database>();
		for (int i = 0; i < databases.getLength(); i++) {		
			Node database = databases.item(i);
			if (database.getNodeName().equals(DATABASE_NODE_NAME)){
				result.add(loadDatabase(database));			
			}
		}		
		return result;
	}

	private Database loadDatabase(Node database) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Node attributeClass = database.getAttributes().getNamedItem(CLASS_ATTRIBUTE_NAME);
		String path = attributeClass.getNodeValue();
		return (Database)classLoader .loadClass(path).newInstance();
	}	

}
