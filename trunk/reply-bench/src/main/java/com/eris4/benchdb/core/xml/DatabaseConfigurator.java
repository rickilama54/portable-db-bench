package com.eris4.benchdb.core.xml;

import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.eris4.benchdb.core.Database;

public class DatabaseConfigurator {
		
	private ClassLoader classLoader = ClassLoader.getSystemClassLoader();
	private NodeList databases;

	public DatabaseConfigurator(NodeList databases) {
		this.databases = databases;
	}
	
	public List<Database> readDatabases() throws ClassNotFoundException, InstantiationException, IllegalAccessException  {
		List<Database> result = new LinkedList<Database>();
		for (int i = 0; i < databases.getLength(); i++) {		
			Node database = databases.item(i);
			if (database.getNodeName().equals(XmlConstants.DATABASE_NODE)){
				result.add(loadDatabase(database));			
			}
		}		
		return result;
	}

	private Database loadDatabase(Node database) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Node attributeClass = database.getAttributes().getNamedItem(XmlConstants.DATABASE_CLASS_ATTRIBUTE);
		String path = attributeClass.getNodeValue();
		return (Database)classLoader .loadClass(path).newInstance();
	}	

}
