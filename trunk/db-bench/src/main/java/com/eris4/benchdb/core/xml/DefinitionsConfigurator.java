package com.eris4.benchdb.core.xml;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DefinitionsConfigurator {

	private static final String CLASS_ATTRIBUTE_NAME = "class";
	private static final String NAME_ATTRIBUTE_NAME = "name";
	private static final String MONITOR_NODE_NAME = "monitor";
	private static final String OPERATION_NODE_NAME = "operation";
	private static final String INITIALIZATOR_NODE_NAME = "initializator";
	private NodeList definitions;

	public DefinitionsConfigurator(NodeList definitions) {
		this.definitions = definitions;
	}
	
	public String getOperationClassFromDefinition(String nodeValue) throws MissingDefinitionException {
		return getDefinition(nodeValue,OPERATION_NODE_NAME);
	}
	
	public String getMonitorClassFromDefinition(String nodeValue) throws MissingDefinitionException {
		return getDefinition(nodeValue,MONITOR_NODE_NAME);
	}
	
	public String getDbInitializatorClassFromDefinition(String nodeValue) throws MissingDefinitionException {
		return getDefinition(nodeValue,INITIALIZATOR_NODE_NAME);
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
