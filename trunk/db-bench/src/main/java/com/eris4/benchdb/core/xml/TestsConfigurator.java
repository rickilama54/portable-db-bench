package com.eris4.benchdb.core.xml;

import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.eris4.benchdb.core.DbInitializator;
import com.eris4.benchdb.core.Operation;
import com.eris4.benchdb.core.Task;
import com.eris4.benchdb.core.Test;
import com.eris4.benchdb.core.monitor.Monitor;

public class TestsConfigurator {

	private static final int DEFAULT_TRANSACTION_PER_SECOND = Integer.MAX_VALUE;
	private static final String NUMBER_OF_OBJECTS_NODE_NAME = "numberOfObjects";
	private static final String TPS_ATTRIBUTE_NAME = "tps";
	private static final String OPERATION_NODE_NAME = "operation";
	private static final String OPERATION_NAME_ATTRIBUTE = "name";
	private static final String MONITOR_NODE_NAME = "monitor";
	private static final String TASK_NODE_NAME = "task";
	private static final String INITIALIZATOR_NODE_NAME = "initializator";
	private static final String TEST_NODE_NAME = "test";
	private static final String TEST_TIME_ATTRIBUTE = "time";
	private static final String TEST_NAME_ATTRIBUTE = "name";
	private static final String OPERATION_REPETITION_ATTRIBUTE = "repetition";
	private NodeList tests;
	private DefinitionsConfigurator definitionsConfigurator;
	private ClassLoader classLoader = ClassLoader.getSystemClassLoader();

	public TestsConfigurator(NodeList tests, NodeList definitions) {
		this.tests = tests;
		definitionsConfigurator = new DefinitionsConfigurator(definitions);
	}
	
	public List<Test> readTests() throws InstantiationException, IllegalAccessException, ClassNotFoundException, MissingDefinitionException {
		List<Test> result = new LinkedList<Test>();
		for (int i = 0; i < tests.getLength(); i++) {
			Node test = tests.item(i);
			if (test.getNodeName().equals(TEST_NODE_NAME)){
				result.add(loadTest(test));			
			}
		}
		return result;
	}

	private Test loadTest(Node test) throws InstantiationException, IllegalAccessException, ClassNotFoundException, MissingDefinitionException {
		List<DbInitializator> dbInizializators = new LinkedList<DbInitializator>();
		List<Task> tasks = new LinkedList<Task>();
		List<Monitor> monitors = new LinkedList<Monitor>();
		NodeList childs = test.getChildNodes();
		for (int i = 0; i < childs.getLength(); i++){
			Node child = childs.item(i);
			String nodeName = child.getNodeName();
			if (nodeName.equals(INITIALIZATOR_NODE_NAME)){
				dbInizializators.add(loadDbInitializator(child));
			}
			else if (nodeName.equals(TASK_NODE_NAME)){
				tasks.add(loadTask(child));
			}
			else if (nodeName.equals(MONITOR_NODE_NAME)){
				monitors.add(loadMonitor(child));
			}
		}		
		long time = Long.parseLong(test.getAttributes().getNamedItem(TEST_TIME_ATTRIBUTE).getNodeValue());
		String name = test.getAttributes().getNamedItem(TEST_NAME_ATTRIBUTE).getNodeValue();
		return new Test(dbInizializators,tasks,monitors,time,name);
	}

	

	private Monitor loadMonitor(Node monitor) throws InstantiationException, IllegalAccessException, ClassNotFoundException, MissingDefinitionException {
		Node attributeName = monitor.getAttributes().getNamedItem(OPERATION_NAME_ATTRIBUTE);
		String path = definitionsConfigurator.getMonitorClassFromDefinition(attributeName.getNodeValue());
		Monitor result = (Monitor) classLoader .loadClass(path).newInstance();
		return result;
	}


	private Task loadTask(Node task) throws InstantiationException, IllegalAccessException, ClassNotFoundException, MissingDefinitionException {
		List<Operation> operations = new LinkedList<Operation>();
		List<Monitor> monitors = new LinkedList<Monitor>();
		NodeList childs = task.getChildNodes();
		for (int i = 0; i < childs.getLength(); i++){
			Node child = childs.item(i);
			if (child.getNodeName().equals(OPERATION_NODE_NAME)){
				operations.add(loadOperation(child));
			}
			if (child.getNodeName().equals(MONITOR_NODE_NAME)){
				monitors.add(loadMonitor(child));
			}
		}				
		Task result = new Task(operations);
		result.setMonitors(monitors);
		Node attributeTps = task.getAttributes().getNamedItem(TPS_ATTRIBUTE_NAME);
		int transactionPerSecond = DEFAULT_TRANSACTION_PER_SECOND;
		if (attributeTps != null){
			transactionPerSecond = Integer.parseInt(attributeTps.getNodeValue());
		}
		result.setTransactionPerSecond(transactionPerSecond);
		return result;
	}
	
	private Operation loadOperation(Node operation) throws InstantiationException, IllegalAccessException, ClassNotFoundException, MissingDefinitionException {
		Node attributeName = operation.getAttributes().getNamedItem(OPERATION_NAME_ATTRIBUTE);
		String path = definitionsConfigurator.getOperationClassFromDefinition(attributeName.getNodeValue());
		Operation result = (Operation) classLoader.loadClass(path).newInstance();
		Node repetitionAttribute = operation.getAttributes().getNamedItem(OPERATION_REPETITION_ATTRIBUTE);
		int repetition = 1;
		if (repetitionAttribute != null) {
			repetition = Integer.parseInt(repetitionAttribute.getNodeValue());
		}		 
		result.setRepetition(repetition);
		return result;
	}

	private DbInitializator loadDbInitializator(Node dbInitializator) throws InstantiationException, IllegalAccessException, ClassNotFoundException, MissingDefinitionException {
		Node attributeName = dbInitializator.getAttributes().getNamedItem(OPERATION_NAME_ATTRIBUTE);
		Node attributeNumberOfObjects = dbInitializator.getAttributes().getNamedItem(NUMBER_OF_OBJECTS_NODE_NAME);
		String path = definitionsConfigurator.getDbInitializatorClassFromDefinition(attributeName.getNodeValue());
		DbInitializator result = (DbInitializator) classLoader.loadClass(path).newInstance();
		int numberOfObjects = Integer.parseInt(attributeNumberOfObjects.getNodeValue());
		result.setNumberOfObjects(numberOfObjects);
		return result;
	}


	

}
