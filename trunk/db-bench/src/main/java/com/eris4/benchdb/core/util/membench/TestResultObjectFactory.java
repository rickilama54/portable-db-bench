package com.eris4.benchdb.core.util.membench;

import com.eris4.benchdb.core.TestResult;

public class TestResultObjectFactory implements ObjectFactory{

	@Override
	public Object makeObject() throws InstantiationException,
			IllegalAccessException {
		
		return new TestResult(1000,2000,3000);
	}

	
}
