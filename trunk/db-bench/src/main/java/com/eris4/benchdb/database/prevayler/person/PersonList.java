package com.eris4.benchdb.database.prevayler.person;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.eris4.benchdb.test.person.domain.Person;


public class PersonList implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1020972974840980388L;
	private Map<Long,PersonPrevayler> persons = new ConcurrentHashMap<Long,PersonPrevayler>();
	
	public void add(PersonPrevayler person){
		persons.put(person.getId(),person);
	}

	public Person get(long id) {		
		return persons.get(id);
	}

	public int size() {
		return persons.size();
	}

}
