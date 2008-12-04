package com.eris4.benchdb.test.person.domain;

public class PersonImpl implements Person {

	private long id;
	private String name;
	

	public String getName() {
		return name;
	}
	

	public void setName(String name) {
		this.name = name;
	}
	

	public long getId() {
		return id;
	}
	

	public void setId(long value) {
		this.id = value;
	}
	
}
