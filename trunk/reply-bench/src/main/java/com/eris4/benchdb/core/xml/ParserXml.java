package com.eris4.benchdb.core.xml;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class ParserXml {
	
	public Document parseXml(String pathname) throws FileNotFoundException, ParserXmlException{
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			return builder.parse(pathname);			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ParserXmlException(e.getMessage());
		}
	}

	public Document parseXml(File resourceFile) throws FileNotFoundException, ParserXmlException {
		return parseXml(resourceFile.getAbsolutePath());
	}

}
