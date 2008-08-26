package com.eris4.benchdb.database.prevayler.account;

import java.util.Date;
import java.util.Map;

import org.prevayler.Transaction;

import com.eris4.benchdb.test.account.domain.Account;

public class WriteAccountTransaction implements Transaction {

	private AccountPrevayler accountPrevayler;

	public WriteAccountTransaction(AccountPrevayler accountPrevayler) {
		this.accountPrevayler = accountPrevayler;
	}

	@Override
	public void executeOn(Object arg0, Date arg1) {
		Map<Integer,Account> accountMap = (Map<Integer, Account>) arg0;
		accountMap.put(new Integer(accountPrevayler.getAccountId()), accountPrevayler);
	}

}
