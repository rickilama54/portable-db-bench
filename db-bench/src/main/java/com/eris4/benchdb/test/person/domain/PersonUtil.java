package com.eris4.benchdb.test.person.domain;

import java.util.Random;

public class PersonUtil {

	private Random random = new Random();
	private static PersonUtil instance = null;
	
	private PersonUtil(){}
	
	public static PersonUtil getInstance(){
		if (instance == null)
			instance = new PersonUtil();
		return instance;
	}
	
	public Person newRandomPerson(){
		return newRandomPerson(new PersonImpl());
	}
	
	public Person newRandomPerson(Person person) {
		createPerson(person);
		return person;
	}
	
	public void copy(Person from, Person to) {
		to.setId(from.getId());
		to.setName(from.getName());
	}
	
	
	
	
	

	private void createPerson(Person person) {
		person.setName(getRandomName());
	}

	private String getRandomName() {
		StringBuilder name = new StringBuilder();
		int length = random.nextInt(20);
		for (int i = 0; i < length; i++) {
			char c = (char) random.nextInt(250);
			name.append(c);
		}		
		return name.toString();
	}


	
	
}
