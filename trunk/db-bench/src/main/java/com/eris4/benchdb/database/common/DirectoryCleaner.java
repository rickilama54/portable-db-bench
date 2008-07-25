package com.eris4.benchdb.database.common;

import java.io.File;

public class DirectoryCleaner {
	
	public static void clean(String directoryName){
		try {
			for (File file : new File(directoryName).listFiles()) {
				boolean b = file.delete();
				if (b==false)
					throw new FileBusyException("cannot delete the file");
			}
		} catch (NullPointerException e) {
			System.out.println("Null Pointer Exception while deleting the db "+directoryName+". Probably the firs time you run this test! Don't worry");
		}		
			
	}

}
