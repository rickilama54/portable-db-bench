package com.eris4.benchdb.core.monitor;

import java.io.File;

public class FileMonitor extends Monitor {
	
	private String fileName;
	
	public FileMonitor(String fileName){
		this.fileName = fileName;
	}
	
	@Override
	public String getDescription() {
		return "File(s) size (bytes)";
	}	
	
	@Override
	public long getValue(){
		if(fileName == null)
			return 0;
		return getFileSize(new File(fileName));
	}
	
	private long getFileSize(File file) {		
		if (file.isDirectory())
			return getDirectorySize(file);
		if (file.isFile())
			return file.length();
//		if (!file.exists())
		return 0;
	}

	private long getDirectorySize(File directory) {
		long result  = 0;
		for (File file : directory.listFiles()) {
			result += getFileSize(file);			
		}
		return result;
	}

}
