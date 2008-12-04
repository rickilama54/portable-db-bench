package com.eris4.benchdb.database.prevayler.person;

import java.io.Serializable;

import com.eris4.benchdb.test.person.domain.Person;

public class PersonPrevayler implements Serializable,Person{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6606331312269957684L;
	private long id;
	private String name;

	
	public long getId() {
		return id;
	}

	
	public void setId(long id) {
		this.id = id;
	}

	
	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name  = name;
	}

}
