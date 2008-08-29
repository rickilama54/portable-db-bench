package com.eris4.benchdb.database.db4o.msisdn;

import java.util.Random;

import org.apache.log4j.Logger;

import com.db4o.ObjectSet;
import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.database.db4o.ObjectContainerSingleton;
import com.eris4.benchdb.test.msisdn.domain.Msisdn;
import com.eris4.benchdb.test.msisdn.domain.MsisdnDriver;
import com.eris4.benchdb.test.msisdn.domain.MsisdnImpl;

public class MsisdnDb4oDriver implements MsisdnDriver {
	
	private ObjectContainerSingleton db;
	Random random = new Random();
	private Logger logger = Logger.getLogger(MsisdnDb4oDriver.class);

	@Override
	public void close() throws TestDriverException {
		logger.debug("close");
	}

	@Override
	public void connect() throws TestDriverException {
		logger.debug("connect");
		db = ObjectContainerSingleton.getInstance();
	}

	@Override
	public int getNumberOfMsisdn() throws TestDriverException {
		return db.queryByExample(new MsisdnImpl()).size();
	}

	@Override
	public void init(int numberOfObject) throws TestDriverException {
		for (int i = 0; i < numberOfObject; i++) {
			Msisdn msisdn = new MsisdnImpl();
			msisdn.setMsisdnId(i);
			msisdn.setAccountId(random.nextInt());
			db.store(msisdn);
		}
		db.commit();
	}

	@Override
	public Msisdn read(int msisdnId) throws TestDriverException {
		Msisdn msisdn = new MsisdnImpl();
		msisdn.setMsisdnId(msisdnId);
		ObjectSet<Msisdn> result = db.queryByExample(msisdn);
		if (result.hasNext()) {
			return result.next();
		}
		if (result.hasNext()) {
			throw new TestDriverException("There is more than 1 msisdn object, while it shouldn't be possible.");
		}
		return null;
	}

	@Override
	public void write(Msisdn msisdn) throws TestDriverException {
		db.store(msisdn);
	}

}
