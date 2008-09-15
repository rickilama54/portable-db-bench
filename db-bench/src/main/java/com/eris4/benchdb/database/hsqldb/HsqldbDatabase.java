package com.eris4.benchdb.database.hsqldb;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.eris4.benchdb.core.Database;
import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.database.common.DirectoryCleaner;
import com.eris4.benchdb.database.hsqldb.account.AccountHsqldbDriver;
import com.eris4.benchdb.database.hsqldb.msisdn.MsisdnHsqldbDriver;
import com.eris4.benchdb.database.hsqldb.person.PersonHsqldbDriver;
import com.eris4.benchdb.database.hsqldb.session.SessionHsqldbDriver;
import com.eris4.benchdb.test.account.domain.AccountDriver;
import com.eris4.benchdb.test.msisdn.domain.MsisdnDriver;
import com.eris4.benchdb.test.person.domain.PersonDriver;
import com.eris4.benchdb.test.session.domain.SessionDriver;

public class HsqldbDatabase extends Database {
	
	public HsqldbDatabase(){
		add(PersonDriver.class, PersonHsqldbDriver.class);
		add(AccountDriver.class, AccountHsqldbDriver.class);
		add(MsisdnDriver.class, MsisdnHsqldbDriver.class);
		add(SessionDriver.class, SessionHsqldbDriver.class);
	}

	@Override
	public void clear() {
		DirectoryCleaner.clean(getFileName());
	}

	@Override
	public String getFileName() {
		return "db/hsqldb";
	}

	private String password = "";
	private String databaseURL = "jdbc:hsqldb:file:db/hsqldb/database";
	private String username = "sa";
    private static final String driver="org.hsqldb.jdbcDriver";
	
	@Override
	public void shutdown() throws TestDriverException, FileNotFoundException,SQLException, IOException, URISyntaxException, ClassNotFoundException {
		Class.forName(driver);
		Connection con = DriverManager.getConnection(databaseURL,username,password);
		con.createStatement().execute("SHUTDOWN");	
	}

}
