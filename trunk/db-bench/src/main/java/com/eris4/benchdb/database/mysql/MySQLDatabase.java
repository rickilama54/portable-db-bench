package com.eris4.benchdb.database.mysql;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.eris4.benchdb.core.Database;
import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.core.util.Resource;
import com.eris4.benchdb.database.common.DirectoryCleaner;
import com.eris4.benchdb.database.mysql.person.PersonMySQLServerDriver;
import com.eris4.benchdb.domain.PersonDriver;

public class MySQLDatabase extends Database {
	
	public static final String DATABASE_NAME = "mysql";
	public static final String LIBRARY_RELATIVE_PATH = "libmysql\\lib";
	public static final String BASEDIR_RELATIVE_PATH = "libmysql";
	private static Logger logger = Logger.getLogger(MySQLDatabase.class);

	public MySQLDatabase(){
		add(PersonDriver.class, PersonMySQLServerDriver.class);
//		add(PersonDriver.class, com.eris4.benchdb.database.mysql.person.PersonMySQLEmbeddedDriver.class);
	}

	@Override
	public void clear() {
		DirectoryCleaner.clean(getFileName());

	}

	@Override
	public String getFileName() {		
		return "db\\"+DATABASE_NAME;
	}
	
	public void shutdown() throws TestDriverException, FileNotFoundException, SQLException, IOException, URISyntaxException{
		PersonMySQLServerDriver driver = new PersonMySQLServerDriver();
//		driver.connect();
		driver.shutdown();
		
	}

	public static Object getBaseDir() {
		String result = null;
		try {
			result =  Resource.getResourceFile(MySQLDatabase.BASEDIR_RELATIVE_PATH).getAbsolutePath();
		} catch (URISyntaxException e) {
			logger.error("error in the url base dir of mysql", e);
		}
		return result;
	}

	public static Object getLibraryPath() {
		String result = null;
		try {
			result =  Resource.getResourceFile(MySQLDatabase.LIBRARY_RELATIVE_PATH).getAbsolutePath();
		} catch (URISyntaxException e) {
			logger.error("error in the url library path of mysql", e);
		}
		return result;
	}

}
