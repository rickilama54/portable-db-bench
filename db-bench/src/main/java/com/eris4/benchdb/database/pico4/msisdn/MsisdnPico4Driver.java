package com.eris4.benchdb.database.pico4.msisdn;

import java.util.Random;

import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.database.pico4.PersistentMapSingleton;
import com.eris4.benchdb.test.msisdn.domain.Msisdn;
import com.eris4.benchdb.test.msisdn.domain.MsisdnDriver;

public class MsisdnPico4Driver implements MsisdnDriver {
	
	private String mapName = "MsisdnPico4";
	private PersistentMapSingleton map;
	private Random random = new Random();

	@Override
	public void close() throws TestDriverException {
		map.close();
	}

	@Override
	public void connect() throws TestDriverException {
		map = PersistentMapSingleton.getInstance(mapName);
		map.load();
	}

	@Override
	public int getNumberOfMsisdn() throws TestDriverException {
		return map.size();
	}

	@Override
	public void init(int numberOfObject) throws TestDriverException {
		for (int i = 0; i < numberOfObject; i++) {
			MsisdnPico4Impl msisdn = new MsisdnPico4Impl();
			msisdn.setMsisdnId(i);
			msisdn.setAccountId(random.nextInt());
			map.put(String.valueOf(i), msisdn);
		}
		map.commit();
	}

	@Override
	public Msisdn read(int msisdnId) throws TestDriverException {
		return (Msisdn) map.get(String.valueOf(msisdnId));
	}

	@Override
	public void write(Msisdn msisdn) throws TestDriverException {
		MsisdnPico4Impl msisdnPico4 = new MsisdnPico4Impl();
		msisdnPico4.setMsisdnId(msisdn.getMsisdnId());
		msisdnPico4.setAccountId(msisdn.getAccountId());
		map.put(String.valueOf(msisdnPico4.getMsisdnId()), msisdnPico4);
	}

}
