package com.eris4.benchdb.database.db4o.msisdn;

import java.util.Random;

import org.apache.log4j.Logger;

import com.db4o.ObjectSet;
import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.database.db4o.ObjectContainerSingleton;
import com.eris4.benchdb.test.msisdn.domain.Msisdn;
import com.eris4.benchdb.test.msisdn.domain.MsisdnDriver;
import com.eris4.benchdb.test.msisdn.domain.MsisdnImpl;
import com.eris4.benchdb.test.person.domain.Person;

public class MsisdnDb4oDriver implements MsisdnDriver {
	
	private ObjectContainerSingleton db;
	Random random = new Random();
	private Logger logger = Logger.getLogger(MsisdnDb4oDriver.class);

	
	public void close() throws TestDriverException {
		logger.debug("close");
	}

	
	public void connect() throws TestDriverException {
		logger.debug("connect");
		db = ObjectContainerSingleton.getInstance();
	}

	
	public int getNumberOfMsisdn() throws TestDriverException {
		return db.queryByExample(new MsisdnImpl()).size();
	}

	
	public void init(int numberOfObject) throws TestDriverException {
		for (int i = 0; i < numberOfObject; i++) {
			Msisdn msisdn = new MsisdnImpl();
			msisdn.setMsisdnId(i);
			msisdn.setAccountId(random.nextInt());
			db.store(msisdn);
		}
		db.commit();
	}

	
	public Msisdn read(int msisdnId) throws TestDriverException {
		Msisdn msisdn = new MsisdnImpl();
		msisdn.setMsisdnId(msisdnId);
		ObjectSet<Msisdn> result = db.queryByExample(msisdn);
		while(result.hasNext()){
			Msisdn tmp = result.next();
			if (tmp.getMsisdnId() == msisdnId){
				return tmp;
			}
		}
		return null;
	}

	
	public void write(Msisdn msisdn) throws TestDriverException {
		db.store(msisdn);
	}

}
