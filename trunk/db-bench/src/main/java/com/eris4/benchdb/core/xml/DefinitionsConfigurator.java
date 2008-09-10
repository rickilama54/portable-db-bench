package com.eris4.benchdb.core.xml;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.eris4.benchdb.core.monitor.Monitor;

public class DefinitionsConfigurator {

	private static final String CLASS_ATTRIBUTE_NAME = "class";
	private static final String NAME_ATTRIBUTE_NAME = "name";
	private static final String MONITOR_NODE_NAME = "monitor";
	private static final String OPERATION_NODE_NAME = "operation";
	private static final String INITIALIZATOR_NODE_NAME = "initializator";
	private NodeList definitions;
	private ClassLoader classLoader = ClassLoader.getSystemClassLoader();

	public DefinitionsConfigurator(NodeList definitions) {
		this.definitions = definitions;
	}
	
	public String getOperationClassFromDefinition(String nodeValue) throws MissingDefinitionException {
		return getDefinition(nodeValue,OPERATION_NODE_NAME);
	}	
	
	public String getDbInitializatorClassFromDefinition(String nodeValue) throws MissingDefinitionException {
		return getDefinition(nodeValue,INITIALIZATOR_NODE_NAME);
	}
	
	public Map<String,Monitor> getTestMonitors() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		return getMonitors(XmlConstants.TEST_MONITORS);
	}
	
	public Map<String,Monitor> getTaskMonitors() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		return getMonitors(XmlConstants.TASK_MONITORS);
	}
	
	private Map<String,Monitor> getMonitors(String nodeName) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Map<String,Monitor> result = new HashMap<String,Monitor>();
		for (int i = 0; i < definitions.getLength(); i++){
			Node child = definitions.item(i);
			if (child.getNodeName().equals(nodeName)){
				String monitorClass =  child.getAttributes().getNamedItem(CLASS_ATTRIBUTE_NAME).getNodeValue();
				String name =  child.getAttributes().getNamedItem(NAME_ATTRIBUTE_NAME).getNodeValue();
				Monitor monitor = (Monitor) classLoader.loadClass(monitorClass).newInstance();
				result.put(name,monitor);
			}
		}
		return result;
	}
	
	

	private String getDefinition(String nodeValue,String nodeName) throws MissingDefinitionException {
		for (int i = 0; i < definitions.getLength(); i++){
			Node child = definitions.item(i);
			if (child.getNodeName().equals(nodeName)){
				if (child.getAttributes().getNamedItem(NAME_ATTRIBUTE_NAME).getNodeValue().equals(nodeValue)){
					return child.getAttributes().getNamedItem(CLASS_ATTRIBUTE_NAME).getNodeValue();
				}
			}
		}
		throw new MissingDefinitionException("Missing "+nodeName+" definition: "+nodeValue);
	}	

}
