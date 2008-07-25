package com.eris4.benchdb.test.person;

import java.util.Random;

import com.eris4.benchdb.core.NoSuitableDriverException;
import com.eris4.benchdb.core.BaseTest;
import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.core.monitor.TimeMonitor;

public class MaxReadTest extends BaseTest {

	private int numberOfObject = 10000;
	private int stopCondition = 15000;//millis	
	private TimeMonitor timeMonitor;
	private PersonDriver personDriver;
	private Random random = new Random();
	private boolean firstTime;
	
	public MaxReadTest() {
		//TODO read properties from file		
	}
	
	public MaxReadTest(int numberOfObject, int stopCondition) {
		this.numberOfObject = numberOfObject;
		this.stopCondition = stopCondition;
	}

	@Override
	public void connect() throws TestDriverException, NoSuitableDriverException {
		personDriver = (PersonDriver) getDatabase().getSpecificDriver(PersonDriver.class);
		personDriver.connect();
	}
		
	@Override
	public void init() throws NoSuitableDriverException, TestDriverException{		
		personDriver.init(numberOfObject);
	}	
	
	@Override
	public void warmUp(){
		for (int i = 0; i < numberOfObject/100; i++) {
			executeTask();
		}
		timeMonitor = new TimeMonitor();
		firstTime = true;
//		System.out.println(">>> DEBUG(task times in the warmup): "+i);
	}

	@Override
	public void executeTask() {	
		if (firstTime){
			firstTime = false;
			timeMonitor.start();
		}
		long id = random.nextInt(numberOfObject);
		Person person = personDriver.read(id);
		if (person == null)
			System.out.println("Missin person with id "+id);		
	}

	@Override
	public boolean isStopped() {
		return timeMonitor.getValue() >= stopCondition;	
	}

	@Override
	public void close() {
		personDriver.close();		
	}

	@Override
	public String getDescription() {
		StringBuilder result = new StringBuilder();
		result.append("\nThis test tell you the max number of read per second the db support for the object Person.");
		result.append("\nThe parameters of the test are:");
		result.append("\n- number of objects in the database: "+numberOfObject);
		result.append("\n- total number of seconds before test stops: "+stopCondition);
		return result.toString();
	}

}
