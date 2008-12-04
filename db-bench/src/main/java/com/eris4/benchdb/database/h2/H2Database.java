package com.eris4.benchdb.database.h2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import com.eris4.benchdb.core.Database;
import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.database.common.DirectoryCleaner;
import com.eris4.benchdb.database.h2.account.AccountH2Driver;
import com.eris4.benchdb.database.h2.msisdn.MsisdnH2Driver;
import com.eris4.benchdb.database.h2.person.PersonH2Driver;
import com.eris4.benchdb.database.h2.session.SessionH2Driver;
import com.eris4.benchdb.test.account.domain.AccountDriver;
import com.eris4.benchdb.test.msisdn.domain.MsisdnDriver;
import com.eris4.benchdb.test.person.domain.PersonDriver;
import com.eris4.benchdb.test.session.domain.SessionDriver;

public class H2Database extends Database {
	
	public H2Database(){
		add(PersonDriver.class, PersonH2Driver.class);
		add(AccountDriver.class, AccountH2Driver.class);
		add(MsisdnDriver.class, MsisdnH2Driver.class);
		add(SessionDriver.class, SessionH2Driver.class);
	}

	
	public void clear() {
		DirectoryCleaner.clean(getFileName());
	}

	
	public String getFileName() {
		return "db/h2";
	}

	
	public void shutdown() throws TestDriverException, FileNotFoundException,SQLException, IOException, URISyntaxException {
		//TODO		
	}

}
