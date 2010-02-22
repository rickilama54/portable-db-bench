package com.eris4.benchdb.database.prevayler;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.prevayler.Prevayler;
import org.prevayler.PrevaylerFactory;
import org.prevayler.Transaction;

public class PrevaylerSingleton {
	
	private Prevayler prevayler;
	private int counter = 0;
	private String key;
	
	public synchronized void close() throws IOException {
		counter --;
		if (counter == 0){
			prevayler.close();
			singleton.remove(key);
		}
		
	}		

	public Object prevalentSystem() {
		return prevayler.prevalentSystem();
	}
	
	public void execute(Transaction transaction) {
		prevayler.execute(transaction);
	}

	public synchronized void takeSnapshot() throws IOException {
		prevayler.takeSnapshot();
	}
	
	private void incrementCounter() {
		counter ++;		
	}
	
	
	
	
	
	/*
	 */
	
	
	private static Map<String,PrevaylerSingleton> singleton;
	
	private PrevaylerSingleton(){}
	
	private PrevaylerSingleton(Serializable serializable, String directory) throws IOException, ClassNotFoundException {		
		this.prevayler = PrevaylerFactory.createPrevayler(serializable,directory);
		this.key = directory;
	}

	public synchronized static PrevaylerSingleton createPrevayler(Serializable serializable,String directory) throws IOException, ClassNotFoundException {
		if (singleton == null){
			singleton = new HashMap<String, PrevaylerSingleton>();
		}
		PrevaylerSingleton prevaylerSingleton = singleton.get(directory);
		if (prevaylerSingleton == null){
			prevaylerSingleton = new PrevaylerSingleton(serializable,directory);
			singleton.put(directory,prevaylerSingleton);
		}
		prevaylerSingleton.incrementCounter();
		return prevaylerSingleton;
	}

	



	
	
	

}
