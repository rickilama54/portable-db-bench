package com.eris4.benchdb;

import com.eris4.benchdb.core.Database;
import com.eris4.benchdb.core.OldTest;
import com.eris4.benchdb.core.Test;
import com.eris4.benchdb.core.TestRunner;
import com.eris4.benchdb.database.pico4.Pico4Database;
import com.eris4.benchdb.database.prevayler.PrevaylerDatabase;
import com.eris4.benchdb.test.person.ConcurrentWriteAndReadTest;
import com.eris4.benchdb.test.person.MaxReadTest;
import com.eris4.benchdb.test.person.MaxWriteTest;
import com.eris4.benchdb.test.person.WriteAndReadTest;

public class Main {	

	public static void main(String[] args) {
//		Test[] tests = new Test[]{
//				new MaxWriteTest(),
//				new MaxReadTest(),
//				new WriteAndReadTest(),
//				new ConcurrentWriteAndReadTest()
//		};
//		Database[] drivers = new Database[]{
//				new PrevaylerDatabase(),
//				new Pico4Database()				
//		};
//		new TestRunner().execute(tests,drivers);		
	}
	
}
