package com.eris4.benchdb.database.pico4;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import com.eris4.benchdb.core.Database;
import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.database.common.DirectoryCleaner;
import com.eris4.benchdb.database.pico4.account.AccountPico4Driver;
import com.eris4.benchdb.database.pico4.msisdn.MsisdnPico4Driver;
import com.eris4.benchdb.database.pico4.person.PersonPico4Driver;
import com.eris4.benchdb.database.pico4.session.SessionPico4Driver;
import com.eris4.benchdb.test.account.domain.AccountDriver;
import com.eris4.benchdb.test.msisdn.domain.MsisdnDriver;
import com.eris4.benchdb.test.person.domain.PersonDriver;
import com.eris4.benchdb.test.session.domain.SessionDriver;

public class Pico4Database extends Database{
	
	public Pico4Database(){
		add(PersonDriver.class, PersonPico4Driver.class);
		add(AccountDriver.class, AccountPico4Driver.class);
		add(MsisdnDriver.class, MsisdnPico4Driver.class);
		add(SessionDriver.class, SessionPico4Driver.class);
	}

	@Override
	public String getFileName() {
		return "db/pico4";
	}

	@Override
	public void clear() {
		DirectoryCleaner.clean(getFileName());
	}

	@Override
	public void shutdown() throws TestDriverException, FileNotFoundException,SQLException, IOException, URISyntaxException {}

}
