package com.eris4.benchdb.core.xml;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.eris4.benchdb.core.DbInitializator;
import com.eris4.benchdb.core.Operation;
import com.eris4.benchdb.core.Task;
import com.eris4.benchdb.core.Test;
import com.eris4.benchdb.core.monitor.Monitor;
import com.eris4.benchdb.core.reporter.Reporter;

public class TestsConfigurator {

	private NodeList tests;
	private DefinitionsConfigurator definitionsConfigurator;
	private ClassLoader classLoader = ClassLoader.getSystemClassLoader();
	private Map<String,Monitor> monitorsMap;

	public TestsConfigurator(NodeList tests, DefinitionsConfigurator definitionsConfigurator) {
		monitorsMap = new HashMap<String,Monitor>();
		this.tests = tests;
		this.definitionsConfigurator = definitionsConfigurator;
	}
	
	
	
	public List<Test> readTests() throws InstantiationException, IllegalAccessException, ClassNotFoundException, MissingDefinitionException {
		List<Test> result = new LinkedList<Test>();
		for (int i = 0; i < tests.getLength(); i++) {
			Node test = tests.item(i);
			if (test.getNodeName().equals(XmlConstants.TEST_NODE)){
				result.add(loadTest(test));			
			}
		}
		return result;
	}

	private Test loadTest(Node test) throws InstantiationException, IllegalAccessException, ClassNotFoundException, MissingDefinitionException {
		List<DbInitializator> dbInizializators = new LinkedList<DbInitializator>();
		List<Task> tasks = new LinkedList<Task>();
		List<Monitor> monitors = new LinkedList<Monitor>();
		List<Reporter> reporters = new LinkedList<Reporter>();
		NodeList childs = test.getChildNodes();
		for (int i = 0; i < childs.getLength(); i++){
			Node child = childs.item(i);
			String nodeName = child.getNodeName();
			if (nodeName.equals(XmlConstants.INITIALIZATOR_NODE)){
				dbInizializators.add(loadDbInitializator(child));
			}
			else if (nodeName.equals(XmlConstants.TASK_NODE)){
				tasks.add(loadTask(child));
			}
			else if (nodeName.equals(XmlConstants.MONITOR_NODE)){
				monitors.add(loadMonitor(child));
			}
			else if (nodeName.equals(XmlConstants.REPORTER_NODE)){
				reporters.add(ReportersConfigurator.loadReporter(child, monitorsMap));// warning: the monitorsMap is initialized only after loading the tasks
			}
		}		
		// time parameter in class Test
//		long time = Long.parseLong(test.getAttributes().getNamedItem(XmlConstants.TEST_TIME_ATTRIBUTE).getNodeValue());
		String name = test.getAttributes().getNamedItem(XmlConstants.TEST_NAME_ATTRIBUTE).getNodeValue();
		return new Test(dbInizializators,tasks,reporters,name);
	}

	

	private Monitor loadMonitor(Node monitor) throws InstantiationException, IllegalAccessException, ClassNotFoundException, MissingDefinitionException {
		Node attributeName = monitor.getAttributes().getNamedItem(XmlConstants.MONITOR_NAME_ATTRIBUTE);
		String path = definitionsConfigurator.getMonitorClassFromDefinition(attributeName.getNodeValue());
		Monitor result = (Monitor) classLoader .loadClass(path).newInstance();
		Node attributeId = monitor.getAttributes().getNamedItem(XmlConstants.MONITOR_ID_ATTRIBUTE);
		if (attributeId != null){
			monitorsMap.put(attributeId.getNodeValue(),result);
		}
		return result;
	}


	private Task loadTask(Node task) throws InstantiationException, IllegalAccessException, ClassNotFoundException, MissingDefinitionException {
		List<Operation> operations = new LinkedList<Operation>();
		List<Monitor> monitors = new LinkedList<Monitor>();
		NodeList childs = task.getChildNodes();
		for (int i = 0; i < childs.getLength(); i++){
			Node child = childs.item(i);
			if (child.getNodeName().equals(XmlConstants.OPERATION_NODE)){
				operations.add(loadOperation(child));
			}
			if (child.getNodeName().equals(XmlConstants.MONITOR_NODE)){
				monitors.add(loadMonitor(child));
			}
		}				
		Task result = new Task(operations);
		result.setMonitors(monitors);
		Node attributeTps = task.getAttributes().getNamedItem(XmlConstants.TASK_TPS_ATTRIBUTE);
		int transactionPerSecond = XmlConstants.DEFAULT_TRANSACTION_PER_SECOND;
		if (attributeTps != null){
			transactionPerSecond = Integer.parseInt(attributeTps.getNodeValue());
		}
		result.setTransactionPerSecond(transactionPerSecond);
		Node attributeTotalTransaction = task.getAttributes().getNamedItem(XmlConstants.TASK_TRANSACTION_ATTRIBUTE);
		int totalTransaction = XmlConstants.DEFAULT_TOTAL_TRANSACTION;
		if (attributeTotalTransaction != null){
			totalTransaction = Integer.parseInt(attributeTotalTransaction.getNodeValue());
		}
		result.setTotalTransaction(totalTransaction);
		Node attributeTime = task.getAttributes().getNamedItem(XmlConstants.TASK_TIME_ATTRIBUTE);
		int time = XmlConstants.DEFAULT_TIME;
		if (attributeTime != null){
			time = Integer.parseInt(attributeTime.getNodeValue());
		}
		result.setTime(time);
		return result;
	}
	
	private Operation loadOperation(Node operation) throws InstantiationException, IllegalAccessException, ClassNotFoundException, MissingDefinitionException {
		Node attributeName = operation.getAttributes().getNamedItem(XmlConstants.OPERATION_NAME_ATTRIBUTE);
		String path = definitionsConfigurator.getOperationClassFromDefinition(attributeName.getNodeValue());
		Operation result = (Operation) classLoader.loadClass(path).newInstance();
		Node repetitionAttribute = operation.getAttributes().getNamedItem(XmlConstants.OPERATION_REPETITION_ATTRIBUTE);
		int repetition = 1;
		if (repetitionAttribute != null) {
			repetition = Integer.parseInt(repetitionAttribute.getNodeValue());
		}		 
		result.setRepetition(repetition);
		return result;
	}

	private DbInitializator loadDbInitializator(Node dbInitializator) throws InstantiationException, IllegalAccessException, ClassNotFoundException, MissingDefinitionException {
		Node attributeName = dbInitializator.getAttributes().getNamedItem(XmlConstants.INITIALIZATOR_NAME_ATTRIBUTE);
		Node attributeNumberOfObjects = dbInitializator.getAttributes().getNamedItem(XmlConstants.INITIALIZATOR_NUMBER_OF_OBJECTS_ATTRIBUTE);
		String path = definitionsConfigurator.getDbInitializatorClassFromDefinition(attributeName.getNodeValue());
		DbInitializator result = (DbInitializator) classLoader.loadClass(path).newInstance();
		int numberOfObjects = Integer.parseInt(attributeNumberOfObjects.getNodeValue());
		result.setNumberOfObjects(numberOfObjects);
		return result;
	}



	public Map<String, Monitor> getMonitorsMap() {
		return monitorsMap;
	}


	

}
