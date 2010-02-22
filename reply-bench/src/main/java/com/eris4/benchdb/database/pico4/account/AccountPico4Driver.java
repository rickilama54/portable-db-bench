package com.eris4.benchdb.database.pico4.account;

import java.util.Random;

import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.database.pico4.PersistentMapSingleton;
import com.eris4.benchdb.test.account.domain.Account;
import com.eris4.benchdb.test.account.domain.AccountDriver;

public class AccountPico4Driver implements AccountDriver {
	
	private String mapName = "AccountPico4";
	private PersistentMapSingleton map;
	private Random random = new Random();

	
	public void close() throws TestDriverException {
		map.close();
	}

	
	public void connect() throws TestDriverException {
		map = PersistentMapSingleton.getInstance(mapName);
		map.load();
	}

	
	public int getNumberOfAccount() throws TestDriverException {
		return map.size();
	}

	
	public void init(int numberOfObject) throws TestDriverException {
		for (int i = 0; i < numberOfObject; i++) {
			AccountPico4Impl account = new AccountPico4Impl();
			account.setAccountId(i);
			account.setBalance(random.nextInt());
			map.put(String.valueOf(i), account);
		}
		map.commit();
	}

	
	public Account read(int accountId) throws TestDriverException {
		return (Account) map.get(String.valueOf(accountId));
	}

	
	public void write(Account account) throws TestDriverException {
		AccountPico4Impl accountPico4 = new AccountPico4Impl();
		accountPico4.setAccountId(account.getAccountId());
		accountPico4.setBalance(account.getBalance());
		map.put(String.valueOf(accountPico4.getAccountId()), accountPico4);
	}

}
