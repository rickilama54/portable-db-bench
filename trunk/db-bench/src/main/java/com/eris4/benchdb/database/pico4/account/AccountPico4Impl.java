package com.eris4.benchdb.database.pico4.account;

import java.io.IOException;

import com.eris4.benchdb.test.account.domain.Account;
import com.eris4.pico4.PICO4PersistentObject;
import com.eris4.pico4.PICO4Serializable;
import com.eris4.pico4.streams.PICO4InputStream;
import com.eris4.pico4.streams.PICO4OutputStream;

public class AccountPico4Impl implements Account, PICO4PersistentObject {

	private int accountId;
	private double balance;
	private int vers = 0;

	
	public int getAccountId() {
		return accountId;
	}

	
	public double getBalance() {
		return balance;
	}

	
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	
	public void setBalance(double balance) {
		this.balance = balance;
	}

	
	public void reload(PICO4Serializable persistentObject) {
		if (persistentObject instanceof AccountPico4Impl) {
			AccountPico4Impl account = (AccountPico4Impl) persistentObject;
			if (account.vers > vers) {
				balance = account.balance;
				vers = account.vers;
				accountId = account.accountId;
			}
		}
	}

	
	public void read(PICO4InputStream reader) throws IOException {
		try {
			vers = reader.readInt();
			accountId = reader.readInt();
			balance = reader.readDouble();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	
	public void write(PICO4OutputStream writer) throws IOException {
		vers++;
		writer.writeInt(vers);
		writer.writeInt(accountId);
		writer.writeDouble(balance);
	}

}
