package com.eris4.benchdb.database.h2.msisdn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.database.h2.H2Database;
import com.eris4.benchdb.test.msisdn.domain.Msisdn;
import com.eris4.benchdb.test.msisdn.domain.MsisdnDriver;
import com.eris4.benchdb.test.msisdn.domain.MsisdnImpl;

public class MsisdnH2Driver implements MsisdnDriver {
	
	private final String WRITE_QUERY = "insert into MSISDN values (?,?)";
	private final String READ_QUERY = "select accountId from MSISDN where msisdnId=?";
	private final String CREATE_TABLE_QUERY = "create table MSISDN (accountId int,msisdnId int primary key)";
	private final String COUNT_MSISDN = "select count(*) from MSISDN"; 
	private String password = "";
	private String databaseURL = "jdbc:h2:file:"+new H2Database().getFileName()+"/database";
	private String username = "sa";
	private String driver="org.h2.Driver";
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
	public int getNumberOfMsisdn() throws TestDriverException {
		int result = 0;
		try {
			PreparedStatement st = con.prepareStatement(COUNT_MSISDN);
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
				writeQuery.setDouble(1,random.nextInt());//accountid
				writeQuery.setInt(2, i);//msisdnId
				writeQuery.executeUpdate();
			}			
		} catch (SQLException e) {
			throw new TestDriverException(e);
		}
	}

	@Override
	public Msisdn read(int msisdnId) throws TestDriverException {
		Msisdn msisdn = null;
		try {
			PreparedStatement st = con.prepareStatement(READ_QUERY);
			st.setInt(1,msisdnId);
			ResultSet rs = st.executeQuery();
			msisdn = new MsisdnImpl();
			if (rs.next()){
				msisdn.setAccountId(rs.getInt(1));
				msisdn.setMsisdnId(msisdnId);
			}
		} catch (SQLException e) {
			throw new TestDriverException(e);
		}
		return msisdn;
	}

	@Override
	public void write(Msisdn msisdn) throws TestDriverException {
		try {
			PreparedStatement st = con.prepareStatement(WRITE_QUERY);
			st.setInt(1, msisdn.getAccountId());
			st.setInt(2,msisdn.getMsisdnId());
			st.executeUpdate();
		} catch (SQLException e) {
			throw new TestDriverException(e);
		}
	}

}
