package com.eris4.benchdb.database.prevayler.account;

import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.prevayler.Transaction;

import com.eris4.benchdb.test.account.domain.Account;

public class InitAccountMapTransaction implements Transaction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7034469677273591066L;
	private int numberOfObject;
	
	public InitAccountMapTransaction(int numberOfObject) {
		this.numberOfObject = numberOfObject;
	}

	@Override
	public void executeOn(Object arg0, Date arg1) {
		Map<Integer,Account> accountMap = (Map<Integer, Account>) arg0;
		Random random = new Random(); 
		for (int i = 0; i < numberOfObject; i++) {
			AccountPrevayler account = new AccountPrevayler();
			account.setAccountId(i);
			account.setBalance(random.nextInt());
			accountMap.put(new Integer(i), account);
		}
	}

}
