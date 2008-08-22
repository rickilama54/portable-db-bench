package com.eris4.benchdb.database.common;

import java.io.File;

import org.apache.log4j.Logger;

public class DirectoryCleaner {
	
	private static Logger logger = Logger.getLogger(DirectoryCleaner.class);
	
	public static void clean(String directoryName){
		try {
			for (File file : new File(directoryName).listFiles()) {
				boolean b = file.delete();
				if (b==false)
					throw new FileBusyException("cannot delete the file");
			}
		} catch (NullPointerException e) {
			logger.warn("Null Pointer Exception while deleting the db "+directoryName+". Probably the first time you run this test! Don't worry");
		}		
			
	}

}
