package com.eris4.benchdb.database.pico4.person;

import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.database.pico4.PersistentMapSingleton;
import com.eris4.benchdb.test.person.domain.Person;
import com.eris4.benchdb.test.person.domain.PersonDriver;
import com.eris4.benchdb.test.person.domain.PersonUtil;

public class PersonPico4Driver implements PersonDriver {
	
	private String mapName = "PersonPico4";	
	private PersistentMapSingleton map;
	private PersonUtil personUtil = PersonUtil.getInstance();
	
	
	public void connect() throws TestDriverException {
		map = PersistentMapSingleton.getInstance(mapName);
		map.load();
	}
	
	
	public void init(int numberOfObject){			
		for (int i = 0; i < numberOfObject; i++) {
			PersonPico4Impl person = new PersonPico4Impl();
			personUtil.newRandomPerson(person);
			person.setId(i);
			map.put(String.valueOf(person.getId()),person);
		}
		map.commit();
	}
	
	
	public void close() {
		map.close();
	}
	
	
	public void write(Person person) {
		PersonPico4Impl personPico4 = new PersonPico4Impl();
		personUtil.copy(person,personPico4);
		map.put(String.valueOf(personPico4.getId()),personPico4);
//		map.commit();
	}
	
	
	public Person read(long l) {
		return (Person) map.get(String.valueOf(l));
	}

	
	public int getNumberOfPerson() {
		return map.size();		
	}	

}
