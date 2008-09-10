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
	private Map<String,Map<String,Monitor>> taskMonitorsMap;
	private Map<String,Monitor> testMonitorsMap;

	public TestsConfigurator(NodeList tests, DefinitionsConfigurator definitionsConfigurator) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		testMonitorsMap = definitionsConfigurator.getTestMonitors();		
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
		Map<String,Task> mapTask = new HashMap<String, Task>();
		testMonitorsMap = definitionsConfigurator.getTestMonitors();
		taskMonitorsMap = new HashMap<String, Map<String,Monitor>>();		
		List<Reporter> reporters = new LinkedList<Reporter>();		
		NodeList childs = test.getChildNodes();
		for (int i = 0; i < childs.getLength(); i++){
			Node child = childs.item(i);
			String nodeName = child.getNodeName();
			if (nodeName.equals(XmlConstants.INITIALIZATOR_NODE)){
				dbInizializators.add(loadDbInitializator(child));
			} else if (nodeName.equals(XmlConstants.TASK_NODE)){
				Task task = loadTask(child);
				mapTask.put(task.getName(), task);
			} else if (nodeName.equals(XmlConstants.LOG_GRAPH_REPORTER_NODE)){
				/*warning: the monitorsMap is initialized only after loading the tasks
				solution: load reporter in a new for cycle, after this 1 finished*/
				reporters.add(ReportersConfigurator.loadLogGraphReporter(child, taskMonitorsMap,testMonitorsMap));
			} else if (nodeName.equals(XmlConstants.LINEAR_GRAPH_REPORTER_NODE)){
				reporters.add(ReportersConfigurator.loadLinearGraphReporter(child, taskMonitorsMap,testMonitorsMap));
			} else if (nodeName.equals(XmlConstants.TEXT_REPORTER_NODE)){
				//TODO
			}
			//aggiungere i monitor TODO
		}		
		// time parameter in class Test
//		long time = Long.parseLong(test.getAttributes().getNamedItem(XmlConstants.TEST_TIME_ATTRIBUTE).getNodeValue());
		String name = test.getAttributes().getNamedItem(XmlConstants.TEST_NAME_ATTRIBUTE).getNodeValue();
		Test result = new Test(dbInizializators,mapTask.values(),reporters,name);
		result.setMonitors(testMonitorsMap.values());
		return result;
	}

	private Task loadTask(Node task) throws InstantiationException, IllegalAccessException, ClassNotFoundException, MissingDefinitionException {
		List<Operation> operations = new LinkedList<Operation>();
		NodeList childs = task.getChildNodes();
		for (int i = 0; i < childs.getLength(); i++){
			Node child = childs.item(i);
			if (child.getNodeName().equals(XmlConstants.OPERATION_NODE)){
				operations.add(loadOperation(child));
			}
		}			
		Task result = new Task(operations);		
		//loading task attributes
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
		Node attributeName = task.getAttributes().getNamedItem(XmlConstants.TASK_NAME_ATTRIBUTE);
		String name = null;
		if (attributeName != null){
			name = attributeName.getNodeValue();
		}
		result.setName(name);
		Map<String, Monitor> taskMonitors = definitionsConfigurator.getTaskMonitors();
		taskMonitorsMap.put(name,taskMonitors);
		result.setMonitors(taskMonitors.values());
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

}
