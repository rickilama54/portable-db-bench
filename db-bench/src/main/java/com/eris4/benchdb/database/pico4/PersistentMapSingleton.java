package com.eris4.benchdb.database.pico4;

import java.util.HashMap;
import java.util.Map;

import com.eris4.pico4.PICO4PersistentObject;
import com.eris4.pico4.PersistentMap;

public class PersistentMapSingleton {
	
	private PersistentMap persitentMap;	
	private String name;
	private int counter = 0;

	public synchronized void close() {
		counter --;
		if (counter == 0){
			persitentMap.close();
			singletonMap.remove(name);
		}
	}		

	public synchronized void load() {
		if (counter == 1){
			persitentMap.load();
		}		
	}
	
	public synchronized void commit() {
		persitentMap.commit();
	}

	public int size() {
		return persitentMap.size();
	}

	public void put(String valueOf, PICO4PersistentObject object) {
		persitentMap.put(valueOf,object);
	}

	public PICO4PersistentObject get(String valueOf) {
		return persitentMap.get(valueOf);
	}
	
	
	
	
	
	
	private static Map<String,PersistentMapSingleton> singletonMap;	
	
	private PersistentMapSingleton(){}
	
	private PersistentMapSingleton(String mapName){
		this.persitentMap = new PersistentMap(mapName);
		this.name = mapName;
		this.counter ++;
	}

	public synchronized static PersistentMapSingleton getInstance(String mapName) {
		if (singletonMap == null){
			singletonMap = new HashMap<String, PersistentMapSingleton>();
		}
		PersistentMapSingleton persistentMapSingleton = singletonMap.get(mapName);
		if(persistentMapSingleton == null){
			persistentMapSingleton = new PersistentMapSingleton(mapName);
			singletonMap.put(mapName,persistentMapSingleton);
		}		
		return persistentMapSingleton;
	}
	
	

}
