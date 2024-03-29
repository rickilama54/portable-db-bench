package com.eris4.benchdb.database.common;

import java.io.File;

import org.apache.log4j.Logger;

public class DirectoryCleaner {
	
	private static Logger logger = Logger.getLogger(DirectoryCleaner.class);
	
	public static void clean(String directoryName){
		logger.info("Deleting file: " + new File(directoryName).getAbsolutePath());
		try {
			for (File file : new File(directoryName).listFiles()) {
				if (file.isDirectory()){
					clean(file.getAbsolutePath()) ;
				}else {
					boolean b = file.delete();
					if (b==false)
						throw new FileBusyException("cannot delete the file: "+file.getAbsolutePath());
				}				
			}
		} catch (NullPointerException e) {
			logger.warn("Null Pointer Exception while deleting the db "+directoryName+". Probably the first time you run this test! Don't worry");
		}		
			
	}

}
