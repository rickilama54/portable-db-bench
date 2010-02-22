package com.eris4.benchdb.database.prevayler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import com.eris4.benchdb.core.Database;
import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.database.common.DirectoryCleaner;
import com.eris4.benchdb.database.prevayler.account.AccountPrevaylerDriver;
import com.eris4.benchdb.database.prevayler.msisdn.MsisdnPrevaylerDriver;
import com.eris4.benchdb.database.prevayler.person.PersonPrevaylerDriver;
import com.eris4.benchdb.database.prevayler.session.SessionPrevaylerDriver;
import com.eris4.benchdb.test.account.domain.AccountDriver;
import com.eris4.benchdb.test.msisdn.domain.MsisdnDriver;
import com.eris4.benchdb.test.person.domain.PersonDriver;
import com.eris4.benchdb.test.session.domain.SessionDriver;

public class PrevaylerDatabase extends Database {
	
	public PrevaylerDatabase(){
		add(PersonDriver.class, PersonPrevaylerDriver.class);
		add(AccountDriver.class, AccountPrevaylerDriver.class);
		add(MsisdnDriver.class, MsisdnPrevaylerDriver.class);
		add(SessionDriver.class, SessionPrevaylerDriver.class);
	}

	
	public String getFileName() {
		return "db/prevayler";
	}

	
	public void clear() {
		DirectoryCleaner.clean(getFileName());
	}
	
	
	public void shutdown() throws TestDriverException, FileNotFoundException,SQLException, IOException, URISyntaxException {}

}
