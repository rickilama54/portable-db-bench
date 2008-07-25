package com.eris4.benchdb.test.person;

import com.eris4.benchdb.core.ConcurrentTest;
import com.eris4.benchdb.core.NoSuitableDriverException;
import com.eris4.benchdb.core.BaseTest;
import com.eris4.benchdb.core.TestDriverException;

public class ConcurrentWriteAndReadTest extends ConcurrentTest {

	private MaxWriteTest write;
	private MaxReadTest read;
	private int numberOfObject = 10000;
	private int readStopCondition = 15000;
	private int writeStopCondition = 15000;
	
	public ConcurrentWriteAndReadTest(){		
		//TODO read properties from file
		write = new MaxWriteTest(numberOfObject,writeStopCondition);
		read = new MaxReadTest(numberOfObject,readStopCondition);
	}

	@Override
	public void init() throws NoSuitableDriverException, TestDriverException {				
		read.connect();	
		read.init();
		write.connect();
	}	
	

	@Override
	public String getDescription() {
		StringBuilder result = new StringBuilder();
		result.append("\nThis test execute concurrently 2 thread: the first write only, while the second read only.");
		result.append("\nThe parameter of the test are:");
		result.append("\n- the number of object that are in the db before the test: "+numberOfObject);
		result.append("\n- the max number of reads before the test stop: "+readStopCondition);
		result.append("\n- the max number of writes before the test stop: "+writeStopCondition);
		return result.toString();
	}

	@Override
	public BaseTest[] getTests() {
		return new BaseTest[]{
				write,
				read
		};
	}	

}
