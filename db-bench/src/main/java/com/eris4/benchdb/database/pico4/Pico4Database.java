package com.eris4.benchdb.database.pico4;

import com.eris4.benchdb.core.Database;
import com.eris4.benchdb.database.common.DirectoryCleaner;
import com.eris4.benchdb.database.pico4.person.PersonPico4Driver;
import com.eris4.benchdb.domain.PersonDriver;

public class Pico4Database extends Database{
	
	public Pico4Database(){
		add(PersonDriver.class, PersonPico4Driver.class);
	}

	@Override
	public String getFileName() {
		return "db/pico4";
	}

	@Override
	public void clear() {
		DirectoryCleaner.clean(getFileName());
	}

}
