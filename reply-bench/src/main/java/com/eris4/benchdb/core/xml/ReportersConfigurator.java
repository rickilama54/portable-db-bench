package com.eris4.benchdb.core.xml;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.eris4.benchdb.core.monitor.Monitor;
import com.eris4.benchdb.core.reporter.LinearGraphReporter;
import com.eris4.benchdb.core.reporter.LogGraphReporter;
import com.eris4.benchdb.core.reporter.Reporter;

public class ReportersConfigurator {

	private NodeList reporters;
	private DefinitionsConfigurator definitionsConfigurator;

	public ReportersConfigurator(NodeList reporters,DefinitionsConfigurator definitionsConfigurator) {
		this.reporters = reporters;
		this.definitionsConfigurator = definitionsConfigurator;
	}

//	public List<Reporter> readReporters(Map<String, Map<String, Monitor>> taskMonitorsMap){
//		List<Reporter> result = new LinkedList<Reporter>();
//		for (int i = 0; i < reporters.getLength(); i++) {		
//			Node reporter = reporters.item(i);
//			if (reporter.getNodeName().equals(XmlConstants.GRAPH_REPORTER_NODE)){
//				result.add(loadReporter(reporter,taskMonitorsMap));			
//			}
//		}		
//		return result;
//	}
	
	public static Reporter loadLogGraphReporter(Node reporter, Map<String, Map<String, Monitor>> taskMonitorsMap, Map<String, Monitor> testMonitorsMap)  {
		return loadGenericGraphReporter(reporter, taskMonitorsMap,testMonitorsMap, new LogGraphReporter());
	}
	
	public static Reporter loadLinearGraphReporter(Node reporter,Map<String, Map<String, Monitor>> taskMonitorsMap,Map<String, Monitor> testMonitorsMap) {
		return loadGenericGraphReporter(reporter, taskMonitorsMap,testMonitorsMap, new LinearGraphReporter());
	}	

	private static Reporter loadGenericGraphReporter(Node reporter,Map<String, Map<String, Monitor>> taskMonitorsMap,Map<String, Monitor> testMonitorsMap, LinearGraphReporter result) {
		String graphName = reporter.getAttributes().getNamedItem(XmlConstants.REPORTER_NAME_ATTRIBUTE).getNodeValue() ;		
		result.setName(graphName);
		NodeList childs = reporter.getChildNodes();
		for (int i = 0; i < childs.getLength(); i++) {
			Node line = childs.item(i);
			if (line.getNodeName().equals(XmlConstants.LINE_NODE)){
				String name = line.getAttributes().getNamedItem(XmlConstants.LINE_NAME_ATTRIBUTE).getNodeValue();
				String x = line.getAttributes().getNamedItem(XmlConstants.LINE_X_ATTRIBUTE).getNodeValue();
				Node xTask = line.getAttributes().getNamedItem(XmlConstants.LINE_X_TASK_ATTRIBUTE);
				Monitor xMonitor = null;
				if (xTask != null){
					xMonitor = taskMonitorsMap.get(xTask.getNodeValue()).get(x);
				} else{
					xMonitor = testMonitorsMap.get(x);
				}					
				String y = line.getAttributes().getNamedItem(XmlConstants.LINE_Y_ATTRIBUTE).getNodeValue();
				Node yTask = line.getAttributes().getNamedItem(XmlConstants.LINE_Y_TASK_ATTRIBUTE);
				Monitor yMonitor = null;
				if (yTask != null){
					yMonitor = taskMonitorsMap.get(yTask.getNodeValue()).get(y);
				} else{
					yMonitor = testMonitorsMap.get(y);
				}	
				result.add(name,xMonitor,yMonitor);
			}			
		}			
		return result;
	}

	

}
