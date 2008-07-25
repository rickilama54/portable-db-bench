package com.eris4.benchdb.core.xml;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.eris4.benchdb.core.monitor.Monitor;
import com.eris4.benchdb.core.reporter.GraphReporter;
import com.eris4.benchdb.core.reporter.Reporter;

public class ReportersConfigurator {

	private NodeList reporters;
	private DefinitionsConfigurator definitionsConfigurator;

	public ReportersConfigurator(NodeList reporters,DefinitionsConfigurator definitionsConfigurator) {
		this.reporters = reporters;
		this.definitionsConfigurator = definitionsConfigurator;
	}

	public List<Reporter> readReporters(Map<String,Monitor> monitorsMap){
		List<Reporter> result = new LinkedList<Reporter>();
		for (int i = 0; i < reporters.getLength(); i++) {		
			Node reporter = reporters.item(i);
			if (reporter.getNodeName().equals(XmlConstants.REPORTER_NODE)){
				result.add(loadReporter(reporter,monitorsMap));			
			}
		}		
		return result;
	}
	
	private Reporter loadReporter(Node reporter, Map<String, Monitor> monitorsMap)  {
		String graphName = reporter.getAttributes().getNamedItem(XmlConstants.REPORTER_NAME_ATTRIBUTE).getNodeValue() ;
		GraphReporter result = new GraphReporter(graphName);
		NodeList childs = reporter.getChildNodes();
		for (int i = 0; i < childs.getLength(); i++) {
			Node line = childs.item(i);
			if (line.getNodeName().equals(XmlConstants.LINE_NODE)){
				String name = line.getAttributes().getNamedItem(XmlConstants.LINE_NAME_ATTRIBUTE).getNodeValue();
				String x = line.getAttributes().getNamedItem(XmlConstants.LINE_X_ATTRIBUTE).getNodeValue();
				String y = line.getAttributes().getNamedItem(XmlConstants.LINE_Y_ATTRIBUTE).getNodeValue();
				result.add(name,monitorsMap.get(x),monitorsMap.get(y));
			}			
		}			
		return result;
	}	

}
