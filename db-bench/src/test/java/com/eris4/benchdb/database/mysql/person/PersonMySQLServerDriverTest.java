package com.eris4.benchdb.database.mysql.person;

import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.core.util.ThreadUtils;
import com.eris4.benchdb.database.mysql.MySQLDatabase;
import com.eris4.benchdb.domain.Person;
import com.eris4.benchdb.domain.PersonImpl;

import junit.framework.TestCase;

public class PersonMySQLServerDriverTest extends TestCase {
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		MySQLDatabase database = new MySQLDatabase();
		database.clear();
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		MySQLDatabase database = new MySQLDatabase();
		database.shutdown();
		ThreadUtils.sleep(5000);
	}

//	public void testShutdown() {
//		fail("Not yet implemented");
//	}

	public void testClose() throws TestDriverException {
		PersonMySQLServerDriver driver = new PersonMySQLServerDriver();		
		driver.connect();
		try {
			driver.close();
		} catch (TestDriverException e) {
			fail(e.getMessage());
		}		
	}

	public void testConnect() throws TestDriverException {
		PersonMySQLServerDriver driver = new PersonMySQLServerDriver();		
		try {
			driver.connect();
		} catch (TestDriverException e) {
			fail(e.getMessage());
		}	
		driver.close();
	}

//	public void testGetNumberOfPerson() {
//		fail("Not yet implemented");
//	}
//
//	public void testInit() {
//		fail("Not yet implemented");
//	}
//
	public void testRead() throws TestDriverException {
		PersonMySQLServerDriver driver = new PersonMySQLServerDriver();
		driver.connect();
		driver.init(0);
		Person person = new PersonImpl();
		person.setName("nome");
		person.setId(1000);
		driver.write(person);
		try {
			Person readedPerson = driver.read(person.getId());
			assertEquals(person.getName(), readedPerson.getName());
		} catch (TestDriverException e) {
			fail(e.getMessage());
		}
		driver.close();	
	}

	public void testWrite() throws TestDriverException {
		PersonMySQLServerDriver driver = new PersonMySQLServerDriver();
		driver.connect();
		driver.init(0);
		Person person = new PersonImpl();
		person.setName("nome");
		person.setId(1000);
		try {
			driver.write(person);
		} catch (TestDriverException e) {
			fail(e.getMessage());
		}
		driver.close();	
	}

}
