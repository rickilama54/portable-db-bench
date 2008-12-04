package com.eris4.benchdb.database.db4o.account;

import java.util.Random;

import org.apache.log4j.Logger;

import com.db4o.ObjectSet;
import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.database.db4o.ObjectContainerSingleton;
import com.eris4.benchdb.database.db4o.session.SessionDb4oDriver;
import com.eris4.benchdb.test.account.domain.Account;
import com.eris4.benchdb.test.account.domain.AccountDriver;
import com.eris4.benchdb.test.account.domain.AccountImpl;
import com.eris4.benchdb.test.person.domain.Person;

public class AccountDb4oDriver implements AccountDriver {
	
	private ObjectContainerSingleton db;
	Random random = new Random();
	private Logger logger = Logger.getLogger(SessionDb4oDriver.class);

	public void close() throws TestDriverException {
		logger.debug("close");
	}

	public void connect() throws TestDriverException {
		logger.debug("connect");
		db = ObjectContainerSingleton.getInstance();
	}
	
	public int getNumberOfAccount() throws TestDriverException {
		return db.queryByExample(new AccountImpl()).size();		
	}

	public void init(int numberOfObject) throws TestDriverException {		
		for (int i = 0; i < numberOfObject; i++) {
			Account account = new AccountImpl();
			account.setAccountId(i);
			account.setBalance(random.nextInt());			
			db.store(account);
		}
		db.commit();
	}

	public Account read(int accountId) throws TestDriverException {
		Account account = new AccountImpl();
		account.setAccountId(accountId);
		ObjectSet<Account> result = db.queryByExample(account);
		while(result.hasNext()){
			Account tmp = result.next();
			if (tmp.getAccountId() == accountId){
				return tmp;
			}
		}
		return null;
	}

	public void write(Account account) throws TestDriverException {
		db.store(account);
	}

	


}
