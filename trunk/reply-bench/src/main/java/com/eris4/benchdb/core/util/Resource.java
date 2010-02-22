package com.eris4.benchdb.core.util;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.log4j.Logger;

public final class Resource {
	private Logger logger = Logger.getLogger(Resource.class);
	
	private static URI getURI(String file) throws URISyntaxException{
		return Resource.class.getResource("/"+file).toURI();
	}
	public static File getResourceFile(String filePath) throws URISyntaxException{
		return new File(getURI(filePath));
	}

	public static File getNewFile(String fileName) throws URISyntaxException{
		File file = new File(Resource.class.getResource("/").toURI());
		return new File(file.getAbsolutePath()+"/"+fileName);
	}
	
	public static InputStream getResourceAsStream(String filePath) throws URISyntaxException{
		return Resource.class.getResourceAsStream("/" + filePath);
	}

}
