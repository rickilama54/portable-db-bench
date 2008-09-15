package com.eris4.benchdb.database.prevayler.account;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.database.prevayler.PrevaylerDatabase;
import com.eris4.benchdb.database.prevayler.PrevaylerSingleton;
import com.eris4.benchdb.test.account.domain.Account;
import com.eris4.benchdb.test.account.domain.AccountDriver;

public class AccountPrevaylerDriver implements AccountDriver {
	
	private PrevaylerSingleton prevayler;
	private Map<Integer,Account> accountMap;
	private static final String DB_NAME = "/account";
	private String directory = new PrevaylerDatabase().getFileName() + DB_NAME;

	@Override
	public void close() throws TestDriverException {
		try {
			prevayler.close();
		} catch (IOException e) {
			throw new TestDriverException(e);
		}
	}

	@Override
	public void connect() throws TestDriverException {
		try {
			prevayler = PrevaylerSingleton.createPrevayler(new ConcurrentHashMap<Integer,Account>(),directory);
			accountMap = (Map<Integer,Account>) prevayler.prevalentSystem();
		} catch (Exception e) {
			throw new TestDriverException(e);
		}
	}

	@Override
	public int getNumberOfAccount() throws TestDriverException {
		return accountMap.size();
	}

	@Override
	public void init(int numberOfObject) throws TestDriverException {
		try {
			prevayler.execute(new InitAccountMapTransaction(numberOfObject));
			prevayler.takeSnapshot();
		} catch (Exception e) {
			throw new TestDriverException(e);
		}
	}

	@Override
	public Account read(int accountId) throws TestDriverException {
		return accountMap.get(new Integer(accountId));
	}

	@Override
	public synchronized void write(Account account) throws TestDriverException {
		AccountPrevayler accountPrevayler = new AccountPrevayler();
		accountPrevayler.setAccountId(account.getAccountId());
		accountPrevayler.setBalance(account.getBalance());
		prevayler.execute(new WriteAccountTransaction(accountPrevayler));
	}

}
