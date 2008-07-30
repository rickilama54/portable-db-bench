package com.eris4.benchdb.database.h2;

import com.eris4.benchdb.core.Database;
import com.eris4.benchdb.database.common.DirectoryCleaner;
import com.eris4.benchdb.database.h2.person.PersonH2Driver;
import com.eris4.benchdb.domain.PersonDriver;

public class H2Database extends Database {
	
	public H2Database(){
		add(PersonDriver.class, PersonH2Driver.class);
	}

	@Override
	public void clear() {
		DirectoryCleaner.clean(getFileName());
	}

	@Override
	public String getFileName() {
		return "db/h2";
	}

}
