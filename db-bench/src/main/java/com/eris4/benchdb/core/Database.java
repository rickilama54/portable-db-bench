package com.eris4.benchdb.core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public abstract class Database {
	
	@SuppressWarnings("unchecked")
	private Map<Class, Class> driverCollection = new HashMap<Class, Class>();
//	private Map<Class, Class[]> driverCollection = new HashMap<Class, Class[]>();
	
	@SuppressWarnings("unchecked")
	protected void add(Class testDriverInterface,Class testDriverImpl){
		driverCollection.put(testDriverInterface, testDriverImpl);
	}

	@SuppressWarnings("unchecked")
	public Object getSpecificDriver(Class testDriver) throws NoSuitableDriverException {
		try {
			Object specificDriver = driverCollection.get(testDriver).newInstance();
			if (specificDriver == null)
				throw new NoSuitableDriverException(testDriver);
			return specificDriver;
		} catch (Exception e) {
			throw new NoSuitableDriverException(testDriver,e);
		}
	}
	
	public abstract void clear(); 

	public abstract String getFileName();

	public abstract void shutdown() throws TestDriverException, FileNotFoundException, SQLException, IOException, URISyntaxException;

	

}
