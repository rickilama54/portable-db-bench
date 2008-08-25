package com.eris4.benchdb.database.pico4.msisdn;

import java.io.IOException;

import com.eris4.benchdb.test.msisdn.domain.Msisdn;
import com.eris4.pico4.PICO4PersistentObject;
import com.eris4.pico4.PICO4Serializable;
import com.eris4.pico4.streams.PICO4InputStream;
import com.eris4.pico4.streams.PICO4OutputStream;

public class MsisdnPico4Impl implements Msisdn, PICO4PersistentObject {
	
	private int accountId;
	private int msisdnId;
	private int vers = 0;

	@Override
	public int getAccountId() {
		return accountId;
	}

	@Override
	public int getMsisdnId() {
		return msisdnId;
	}

	@Override
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	@Override
	public void setMsisdnId(int msisdnId) {
		this.msisdnId = msisdnId;
	}

	@Override
	public void reload(PICO4Serializable persistentObject) {
		if (persistentObject instanceof MsisdnPico4Impl) {
			MsisdnPico4Impl msisdn = (MsisdnPico4Impl) persistentObject;
			if(msisdn.vers > vers) {
				msisdnId = msisdn.msisdnId;
				vers = msisdn.vers;
				accountId = msisdn.accountId;
			}
		}
	}

	@Override
	public void read(PICO4InputStream reader) throws IOException {
		try {
			vers = reader.readInt();
			accountId = reader.readInt();
			msisdnId = reader.readInt();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void write(PICO4OutputStream writer) throws IOException {
		vers++;
		writer.writeInt(vers);
		writer.writeInt(accountId);
		writer.writeInt(msisdnId);
	}

}
