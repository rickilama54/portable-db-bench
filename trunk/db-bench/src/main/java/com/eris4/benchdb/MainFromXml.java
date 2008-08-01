package com.eris4.benchdb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import com.eris4.benchdb.core.Database;
import com.eris4.benchdb.core.Printer;
import com.eris4.benchdb.core.Test;
import com.eris4.benchdb.core.TestRunner;
import com.eris4.benchdb.core.util.Resource;
import com.eris4.benchdb.core.xml.BenchConfigurator;
import com.lowagie.text.Chapter;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

public class MainFromXml {

	private static final String FILE_NAME = "bench-properties.xml";

	public static void main(String[] args) throws Exception {
		System.out.println("Loading xml ...");
		BenchConfigurator configurator = new BenchConfigurator(FILE_NAME);
		List<Database> databases = configurator.readDatabases();
		List<Test> tests = configurator.readTests();			
		System.out.println("Number of tests: "+tests.size());
		System.out.println("Number of databases: "+databases.size());
		new TestRunner().execute(tests,databases);		
		new Printer().print(tests,databases);
	}


	
}
