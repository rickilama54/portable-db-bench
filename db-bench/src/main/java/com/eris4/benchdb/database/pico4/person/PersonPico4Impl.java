package com.eris4.benchdb.database.pico4.person;

import java.io.IOException;

import com.eris4.benchdb.test.person.domain.Person;
import com.eris4.pico4.PICO4PersistentObject;
import com.eris4.pico4.PICO4Serializable;
import com.eris4.pico4.streams.PICO4InputStream;
import com.eris4.pico4.streams.PICO4OutputStream;

public class PersonPico4Impl implements Person, PICO4PersistentObject {

	private long id;
	private int vers = 0;
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
	public void reload(PICO4Serializable persistentObject) {
		if (persistentObject instanceof PersonPico4Impl) {
			PersonPico4Impl person = (PersonPico4Impl) persistentObject;
			if (person.vers > vers) {
				id = person.id;// the id shouldn't change
				name = person.name;
				vers = person.vers;
			}
		}
	}

	@Override
	public void read(PICO4InputStream reader) throws IOException {
		try {
			vers = reader.readInt();
			id = reader.readLong();
			name = reader.readString();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void write(PICO4OutputStream writer) throws IOException {
		vers++;
		writer.writeInt(vers);
		writer.writeLong(id);
		writer.writeString(name);
		writer.flush();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

}
