package com.eris4.benchdb.database.hsqldb.session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.database.h2.H2Database;
import com.eris4.benchdb.database.hsqldb.HsqldbDatabase;
import com.eris4.benchdb.test.account.domain.AccountImpl;
import com.eris4.benchdb.test.session.domain.Session;
import com.eris4.benchdb.test.session.domain.SessionDriver;
import com.eris4.benchdb.test.session.domain.SessionImpl;

public class SessionHsqldbDriver implements SessionDriver {
	
	private final String WRITE_QUERY = "insert into SESSION values (?,?,?)";
	private final String READ_QUERY = "select accountId,startTime from SESSION where sessionId=?";
	private final String CREATE_TABLE_QUERY = "create table SESSION (accountId int,startTime bigint,sessionId int primary key)";
	private final String COUNT_SESSION = "select count(*) from SESSION"; 
	private String password = "";
	private String databaseURL = "jdbc:hsqldb:file:"+new HsqldbDatabase().getFileName()+"/database";
	private String username = "sa";
    private static final String driver="org.hsqldb.jdbcDriver";
	private Connection con;
	private Random random = new Random();

	@Override
	public void close() throws TestDriverException {
		try {
			con.close();
		} catch (SQLException e) {
			throw new TestDriverException(e);
		}
	}

	@Override
	public void connect() throws TestDriverException {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(databaseURL, username, password);
		} catch (SQLException e) {
			throw new TestDriverException(e);
		} catch (ClassNotFoundException e) {
			throw new TestDriverException(e);
		}
	}

	@Override
	public int getNumberOfSession() throws TestDriverException {
		int result = 0;
		try {
			PreparedStatement st = con.prepareStatement(COUNT_SESSION);
			ResultSet rs = st.executeQuery();
			if (rs.next()){
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new TestDriverException(e);
		}
		return result;
	}

	@Override
	public void init(int numberOfObject) throws TestDriverException {
		try {
			PreparedStatement createTableQuery = con.prepareStatement(CREATE_TABLE_QUERY);
			createTableQuery.executeUpdate();
			PreparedStatement writeQuery = con.prepareStatement(WRITE_QUERY);
			for (int i = 0; i < numberOfObject; i++) {
				writeQuery.setInt(1,random.nextInt());
				writeQuery.setLong(2, System.currentTimeMillis());
				writeQuery.setInt(3,i);
				writeQuery.executeUpdate();
			}			
		} catch (SQLException e) {
			throw new TestDriverException(e);
		}
	}

	@Override
	public Session read(int sessionId) throws TestDriverException {
		Session session = null;
		try {
			PreparedStatement st = con.prepareStatement(READ_QUERY);
			st.setInt(1, sessionId);
			ResultSet rs = st.executeQuery();
			session = new SessionImpl();
			if (rs.next()){
				session.setSessionId(sessionId);
				session.setAccountId(rs.getInt(1));
				session.setStartTime(rs.getLong(2));
			}
		} catch (SQLException e) {
			throw new TestDriverException(e);
		}
		return session;
	}

	@Override
	public void write(Session session) throws TestDriverException {
		try {
			PreparedStatement st = con.prepareStatement(WRITE_QUERY);
			st.setInt(1,session.getAccountId());
			st.setLong(2,session.getStartTime());
			st.setInt(3,session.getSessionId());
			st.executeUpdate();
		} catch (SQLException e) {
			throw new TestDriverException(e);
		}
	}

}
