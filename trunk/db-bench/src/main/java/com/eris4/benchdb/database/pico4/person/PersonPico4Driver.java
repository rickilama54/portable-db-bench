package com.eris4.benchdb.database.pico4.person;

import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.test.person.Person;
import com.eris4.benchdb.test.person.PersonDriver;
import com.eris4.benchdb.test.person.PersonUtil;
import com.eris4.pico4.PersistentMap;

public class PersonPico4Driver implements PersonDriver {
	
	private String mapName = "PersonPico4";	
	private PersistentMap map;
	private PersonUtil personUtil = PersonUtil.getInstance();
	
	@Override
	public void connect() throws TestDriverException {
		map = new PersistentMap(mapName);
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
	
	@Override
	public void close() {
		map.close();
	}
	
	@Override
	public void write(Person person) {
		PersonPico4Impl personPico4 = new PersonPico4Impl();
		personUtil.copy(person,personPico4);
		map.put(String.valueOf(personPico4.getId()),personPico4);
//		map.commit();
	}
	
	@Override
	public Person read(long l) {
		return (Person) map.get(String.valueOf(l));
	}

	@Override
	public int getNumberOfPerson() {
		return map.size();
	}	

}
