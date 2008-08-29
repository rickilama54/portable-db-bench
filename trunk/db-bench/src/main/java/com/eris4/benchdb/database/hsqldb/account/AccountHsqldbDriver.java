package com.eris4.benchdb.database.hsqldb.account;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.database.h2.H2Database;
import com.eris4.benchdb.database.hsqldb.HsqldbDatabase;
import com.eris4.benchdb.test.account.domain.Account;
import com.eris4.benchdb.test.account.domain.AccountDriver;
import com.eris4.benchdb.test.account.domain.AccountImpl;

public class AccountHsqldbDriver implements AccountDriver {
	
	private final String WRITE_QUERY = "insert into ACCOUNT values (?,?)";
	private final String READ_QUERY = "select balance from ACCOUNT where accountId=?";
	private final String CREATE_TABLE_QUERY = "create table ACCOUNT (balance int,accountId int primary key)";
	private final String COUNT_ACCOUNT = "select count(*) from ACCOUNT"; 
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
	public int getNumberOfAccount() throws TestDriverException {
		int result = 0;
		try {
			PreparedStatement st = con.prepareStatement(COUNT_ACCOUNT);
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
				writeQuery.setDouble(1,random.nextInt());
				writeQuery.setInt(2, i);
				writeQuery.executeUpdate();
			}			
		} catch (SQLException e) {
			throw new TestDriverException(e);
		}
	}

	@Override
	public Account read(int accountId) throws TestDriverException {
		Account account = null;
		try {
			PreparedStatement st = con.prepareStatement(READ_QUERY);
			st.setInt(1, accountId);
			ResultSet rs = st.executeQuery();
			account = new AccountImpl();
			if (rs.next()){
				account.setAccountId(accountId);
				account.setBalance(rs.getInt(1));
			}
		} catch (SQLException e) {
			throw new TestDriverException(e);
		}
		return account;
	}

	@Override
	public void write(Account account) throws TestDriverException {
		try {
			PreparedStatement st = con.prepareStatement(WRITE_QUERY);
			st.setDouble(1,account.getBalance());
			st.setInt(2,account.getAccountId());
			st.executeUpdate();
		} catch (SQLException e) {
			throw new TestDriverException(e);
		}
	}

}
