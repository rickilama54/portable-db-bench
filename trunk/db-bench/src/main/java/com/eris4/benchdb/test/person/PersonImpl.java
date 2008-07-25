package com.eris4.benchdb.test.person;

public class PersonImpl implements Person {

	private long id;
	private String name;
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public long getId() {
		return id;
	}
	
	@Override
	public void setId(long value) {
		this.id = value;
	}
	
}
