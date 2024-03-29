package com.eris4.benchdb.database.db4o.person;

import java.util.Random;

import org.apache.log4j.Logger;

import com.db4o.ObjectSet;
import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.core.util.StringGenerator;
import com.eris4.benchdb.database.db4o.ObjectContainerSingleton;
import com.eris4.benchdb.test.person.domain.Person;
import com.eris4.benchdb.test.person.domain.PersonDriver;
import com.eris4.benchdb.test.person.domain.PersonImpl;
import com.eris4.benchdb.test.session.domain.Session;
import com.eris4.benchdb.test.session.domain.SessionImpl;

public class PersonDb4oDriver implements PersonDriver {
	
	private ObjectContainerSingleton db;
	Random random = new Random();
	private Logger logger = Logger.getLogger(PersonDb4oDriver.class);
	
	
	public void close() throws TestDriverException {
		logger.debug("close");
	}

	
	public void connect() throws TestDriverException {
		logger.debug("connect");
		db = ObjectContainerSingleton.getInstance();
	}

	
	public int getNumberOfPerson() throws TestDriverException {
		return db.queryByExample(new PersonImpl()).size();
	}

	
	public void init(int numberOfObject) throws TestDriverException {
		StringGenerator stringGenerator = new StringGenerator();
		for (int i = 0; i < numberOfObject; i++) {
			Person person = new PersonImpl();
			person.setId(i);
			person.setName(stringGenerator.getRandomString());
			db.store(person);
		}
		db.commit();
	}

	
	public Person read(long personId) throws TestDriverException {
		Person person = new PersonImpl();
		person.setId(personId);
		ObjectSet<Person> result = db.queryByExample(person);
		while(result.hasNext()){
			Person tmp = result.next();
			if (tmp.getId() == personId){
				return tmp;
			}
		}
		return null;
	}

	
	public void write(Person person) throws TestDriverException {
		db.store(person);
	}

}
