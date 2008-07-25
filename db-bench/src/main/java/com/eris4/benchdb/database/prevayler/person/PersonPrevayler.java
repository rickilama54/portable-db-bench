package com.eris4.benchdb.database.prevayler.person;

import java.io.Serializable;

import com.eris4.benchdb.test.person.Person;

public class PersonPrevayler implements Serializable,Person{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6606331312269957684L;
	private long id;
	private String name;

	@Override
	public long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name  = name;
	}

}
