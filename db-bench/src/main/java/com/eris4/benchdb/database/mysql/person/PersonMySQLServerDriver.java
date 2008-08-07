package com.eris4.benchdb.database.mysql.person;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.core.util.Resource;
import com.eris4.benchdb.core.util.StringGenerator;
import com.eris4.benchdb.core.util.ThreadUtils;
import com.eris4.benchdb.database.mysql.MySQLDatabase;
import com.eris4.benchdb.domain.Person;
import com.eris4.benchdb.domain.PersonDriver;
import com.eris4.benchdb.domain.PersonImpl;

public class PersonMySQLServerDriver implements PersonDriver {
	
	
	private StringGenerator stringGenerator = new StringGenerator();
	private final String WRITE_QUERY = "INSERT INTO PERSON VALUES (?,?)";
	private final String READ_QUERY = "select name from PERSON where id=?";
	private final String CREATE_TABLE_QUERY = "CREATE TABLE PERSON (name varchar(20), id bigint)";
	private final String COUNT_PERSON = "select count(*) from PeRSON";
	private Connection con;
	private Logger logger = Logger.getLogger(PersonMySQLServerDriver.class);
	
	private static final String baseDir = "C:\\Users\\error0\\Documents\\universita\\tesi\\workspace\\db-bench\\mysql-basedir";
	private static final String dataDir = "C:\\Users\\error0\\Documents\\universita\\tesi\\workspace\\db-bench\\db";
	private static final String driver="com.mysql.jdbc.Driver";
    private static final String databaseURL="jdbc:mysql:mxj://localhost/mysql" +
    		"?" +
    		"server.basedir=" + baseDir +
    		"&" +
    		"server.datadir=" + dataDir;

	
	
	
	public void shutdown() throws SQLException, FileNotFoundException, IOException, URISyntaxException{
		String pid = new BufferedReader(new FileReader(dataDir+"\\MysqldResource.pid")).readLine();
		Runtime.getRuntime().exec("taskkill /PID "+pid);
		logger.debug("killing mysql process "+pid);
	}

	@Override
	public void close() throws TestDriverException {
		logger.trace("close");
		try {
			con.close();
			if (1==0)
				throw new SQLException();
		} catch (SQLException e) {
			throw new TestDriverException(e);
		}
	}

	@Override
	public void connect() throws TestDriverException {
		logger.trace("connect");
		try {
			Class.forName(driver);
			logger.debug("Myslq driver registered");			
			con = DriverManager.getConnection(databaseURL,"root","");
			logger.debug("Mysql connection acquired");			
		} catch (SQLException e) {
			throw new TestDriverException(e);
		} catch (ClassNotFoundException e) {
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
		logger.trace("number of person "+result);
		return result;
	}

	@Override
	public void init(int numberOfObject) throws TestDriverException {
		logger.trace("init: "+numberOfObject);
		try {
			PreparedStatement createStatement = con.prepareStatement(CREATE_TABLE_QUERY);
			createStatement.executeUpdate();
			PreparedStatement writeStatement = con.prepareStatement(WRITE_QUERY);
			for (int i = 0; i < numberOfObject; i++) {
				writeStatement.setString(1, stringGenerator.getRandomString());
				writeStatement.setLong(2, i);
				writeStatement.executeUpdate();
			}
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
		logger.debug("person -> name: "+person.getName()+" id: "+person.getId());
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
