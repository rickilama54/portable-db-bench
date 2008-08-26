package com.eris4.benchdb.database.pico4.session;

import java.util.Random;

import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.test.session.domain.Session;
import com.eris4.benchdb.test.session.domain.SessionDriver;
import com.eris4.pico4.PersistentMap;

public class SessionPico4Driver implements SessionDriver {
	
	private String mapName = "SessionPico4";
	private PersistentMap map;
	private Random random = new Random();

	@Override
	public void close() throws TestDriverException {
		map.close();
	}

	@Override
	public void connect() throws TestDriverException {
		map = new PersistentMap(mapName);
		map.load();
	}

	@Override
	public int getNumberOfSession() throws TestDriverException {
		return map.size();
	}

	@Override
	public void init(int numberOfObject) throws TestDriverException {
		for (int i = 0; i < numberOfObject; i++) {
			SessionPico4Impl session = new SessionPico4Impl();
			session.setSessionId(i);
			session.setAccountId(random.nextInt());
			session.setStartTime(System.currentTimeMillis());
			map.put(String.valueOf(i), session);
		}
	}

	@Override
	public Session read(int sessionId) throws TestDriverException {
		return (Session) map.get(String.valueOf(sessionId));
	}

	@Override
	public void write(Session session) throws TestDriverException {
		SessionPico4Impl sessionPico4 = new SessionPico4Impl();
		sessionPico4.setAccountId(session.getAccountId());
		sessionPico4.setSessionId(session.getSessionId());
		sessionPico4.setStartTime(session.getStartTime());
		map.put(String.valueOf(sessionPico4.getSessionId()), sessionPico4);
	}

}
