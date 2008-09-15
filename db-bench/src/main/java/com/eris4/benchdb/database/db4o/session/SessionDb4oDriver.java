package com.eris4.benchdb.database.db4o.session;

import java.util.Random;

import org.apache.log4j.Logger;

import com.db4o.ObjectSet;
import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.database.db4o.ObjectContainerSingleton;
import com.eris4.benchdb.test.person.domain.Person;
import com.eris4.benchdb.test.session.domain.Session;
import com.eris4.benchdb.test.session.domain.SessionDriver;
import com.eris4.benchdb.test.session.domain.SessionImpl;

public class SessionDb4oDriver implements SessionDriver {
	
	private ObjectContainerSingleton db;
	Random random = new Random();
	private Logger logger = Logger.getLogger(SessionDb4oDriver.class);

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
	public int getNumberOfSession() throws TestDriverException {
		return db.queryByExample(new SessionImpl()).size();
	}

	@Override
	public void init(int numberOfObject) throws TestDriverException {		
		for (int i = 0; i < numberOfObject; i++) {
			Session session = new SessionImpl();
			session.setSessionId(i);
			session.setAccountId(random.nextInt());
			session.setStartTime(System.currentTimeMillis());
			db.store(session);
		}
		db.commit();
	}

	@Override
	public Session read(int sessionId) throws TestDriverException {
		Session session = new SessionImpl();
		session.setSessionId(sessionId);
		ObjectSet<Session> result = db.queryByExample(session);
		while(result.hasNext()){
			Session tmp = result.next();
			if (tmp.getSessionId() == sessionId){
				return tmp;
			}
		}
		return null;
	}

	@Override
	public void write(Session session) throws TestDriverException {
		db.store(session);
	}

}
