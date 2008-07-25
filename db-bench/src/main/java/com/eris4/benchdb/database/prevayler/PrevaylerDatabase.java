package com.eris4.benchdb.database.prevayler;

import com.eris4.benchdb.core.Database;
import com.eris4.benchdb.database.common.DirectoryCleaner;
import com.eris4.benchdb.database.prevayler.person.PersonPrevaylerDriver;
import com.eris4.benchdb.test.person.PersonDriver;

public class PrevaylerDatabase extends Database {
	
	public PrevaylerDatabase(){
		add(PersonDriver.class, PersonPrevaylerDriver.class);
	}

	@Override
	public String getFileName() {
		return "db/prevayler";
	}

	@Override
	public void clear() {
		DirectoryCleaner.clean(getFileName());
	}

}
