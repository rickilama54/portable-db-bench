package com.eris4.benchdb.test.person;

import com.eris4.benchdb.core.NoSuitableDriverException;
import com.eris4.benchdb.core.BaseTest;
import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.core.monitor.TimeMonitor;

public class MaxWriteTest extends BaseTest {
	
	private int numberOfObject = 10000;
	private int id;
	private int stopCondition = 15000;	
	private PersonDriver personDriver;
	private PersonUtil personUtil = PersonUtil.getInstance();
	private TimeMonitor timeMonitor;
	private boolean firstTime;
	
	
	public MaxWriteTest() {
		//TODO read properties from file		
	}
	
	public MaxWriteTest(int numberOfObject, int stopCondition) {
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
		id = 1;
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
		Person person = personUtil.newRandomPerson();
		person.setId(id+numberOfObject);
		personDriver.write(person);
		id++;
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
		result.append("\nThis test tell you the max number of write per second the db support for the object Person.");
		result.append("\nThe parameter of the test are:");
		result.append("\n- number of objects in the database: "+numberOfObject);
		result.append("\n- total number of seconds before test stops: "+stopCondition);
		return result.toString();
	}
	
}
