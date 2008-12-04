package com.eris4.benchdb.database.db4o;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import com.eris4.benchdb.core.Database;
import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.database.common.DirectoryCleaner;
import com.eris4.benchdb.database.db4o.account.AccountDb4oDriver;
import com.eris4.benchdb.database.db4o.msisdn.MsisdnDb4oDriver;
import com.eris4.benchdb.database.db4o.person.PersonDb4oDriver;
import com.eris4.benchdb.database.db4o.session.SessionDb4oDriver;
import com.eris4.benchdb.test.account.domain.AccountDriver;
import com.eris4.benchdb.test.msisdn.domain.MsisdnDriver;
import com.eris4.benchdb.test.person.domain.PersonDriver;
import com.eris4.benchdb.test.session.domain.SessionDriver;

public class Db4oDatabase extends Database {
		
	public Db4oDatabase(){
		add(SessionDriver.class,SessionDb4oDriver.class);
		add(AccountDriver.class,AccountDb4oDriver.class);
		add(PersonDriver.class,PersonDb4oDriver.class);
		add(MsisdnDriver.class,MsisdnDb4oDriver.class);
	}

	

	
	public void clear() {
		DirectoryCleaner.clean("db/db4o");
	}

	
	public String getFileName() {
		return "db/db4o";
	}

	
	public void shutdown() throws TestDriverException, FileNotFoundException,SQLException, IOException, URISyntaxException {
		ObjectContainerSingleton.getInstance().shutdown();
	}


}
