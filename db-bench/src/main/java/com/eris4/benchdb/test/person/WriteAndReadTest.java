package com.eris4.benchdb.test.person;

import java.util.Random;

import com.eris4.benchdb.core.NoSuitableDriverException;
import com.eris4.benchdb.core.BaseTest;
import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.core.monitor.TimeMonitor;

public class WriteAndReadTest extends BaseTest {
	
	private int numberOfObject = 1000000;
	private int i;
	private int stopCondition = 15000;	
	private PersonDriver personDriver;
	private PersonUtil personUtil = PersonUtil.getInstance();
	private Random random = new Random();
	private TimeMonitor timeMonitor;
	private boolean firstTime;
	
	public WriteAndReadTest(){
		//TODO read properties from file
	}
	
	@Override
	public void connect() throws TestDriverException, NoSuitableDriverException {
		i = 0;
		personDriver = (PersonDriver) getDatabase().getSpecificDriver(PersonDriver.class);
		personDriver.connect();
	}

	@Override
	public void init() throws NoSuitableDriverException, TestDriverException{
		personDriver.init(numberOfObject);		
	}	
	
	@Override
	public void warmUp(){
		i = 1;
		for (int i = 0; i < numberOfObject/100; i++) {
			executeTask();
		}
		timeMonitor = new TimeMonitor();
		firstTime = true;
	}

	@Override
	public void executeTask() {
		if (firstTime){
			firstTime = false;
			timeMonitor.start();
		}
		personDriver.write(personUtil.newRandomPerson());//TODO id
		long id = random.nextInt(numberOfObject);
		Person person = personDriver.read(id);
		if (person == null)
			System.out.println("Missin person with id "+id);
		i++;
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
		result.append("\nThis test tell you the max number of write+read transactions per second the db support for the object Person.");
		result.append("\nThe parameters of the test are:");
		result.append("\n- Number of objects in the database: "+numberOfObject);
		result.append("\n- Total number of seconds before test stops: "+stopCondition);
		return result.toString();
	}

}
