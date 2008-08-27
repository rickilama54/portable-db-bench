package com.eris4.benchdb.database.hsqldb.person;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.core.util.StringGenerator;
import com.eris4.benchdb.database.h2.H2Database;
import com.eris4.benchdb.test.person.domain.Person;
import com.eris4.benchdb.test.person.domain.PersonDriver;
import com.eris4.benchdb.test.person.domain.PersonImpl;

public class PersonHsqldbDriver implements PersonDriver {
	
	private StringGenerator stringGenerator = new StringGenerator();
	private final String WRITE_QUERY = "insert into PERSON values (?,?)";
	private final String READ_QUERY = "select name from PERSON where id=?";
	private final String CREATE_TABLE_QUERY = "create table PERSON (name varchar,id bigint primary key)";
	private final String COUNT_PERSON = "select count(*) from PeRSON"; 
	private String password = "";
	private String databaseURL = "jdbc:hsqldb:file:"+new H2Database().getFileName()+"/database";
	private String username = "sa";
    private static final String driver="org.hsqldb.jdbcDriver";
	private Connection con;

	@Override
	public void close() throws TestDriverException {
		try {
//			con.createStatement().execute("SHUTDOWN");
			con.close();
		} catch (SQLException e) {
			throw new TestDriverException(e);
		}
	}

	@Override
	public void connect() throws TestDriverException {
		try {
			DriverManager.registerDriver(new org.h2.Driver());
			con = DriverManager.getConnection(databaseURL,username,password);
		} catch (SQLException e) {
			throw new TestDriverException(e);
		}
	}

	@Override
	public int getNumberOfPerson() throws TestDriverException {
		int result = 0;
		try {
			PreparedStatement st = con.prepareStatement(COUNT_PERSON);
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
			PreparedStatement createStatement = con.prepareStatement(CREATE_TABLE_QUERY);
			createStatement.executeUpdate();
			PreparedStatement writeStatement = con.prepareStatement(WRITE_QUERY);
			for (int i = 0; i < numberOfObject; i++) {
				writeStatement.setString(1, stringGenerator.getRandomString());
				writeStatement.setLong(2, i);
				writeStatement.executeUpdate();
			}
			con.createStatement().execute("SHUTDOWN");//TODO da sistemare!!!!
		} catch (SQLException e) {
			throw new TestDriverException(e);
		}
	}

	@Override
	public Person read(long personId) throws TestDriverException {
		Person person = null;
		try {
			PreparedStatement st = con.prepareStatement(READ_QUERY);
			st.setLong(1, personId);
			ResultSet rs = st.executeQuery();
			person = new PersonImpl();
			if (rs.next()){
				person.setName(rs.getString(1));
				person.setId(personId);
			}
		} catch (SQLException e) {
			throw new TestDriverException(e);
		}
		return person;
	}

	@Override
	public void write(Person person) throws TestDriverException {
		try {
			PreparedStatement st = con.prepareStatement(WRITE_QUERY);
			st.setString(1, person.getName());
			st.setLong(2, person.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			throw new TestDriverException(e);
		}
	}

}
