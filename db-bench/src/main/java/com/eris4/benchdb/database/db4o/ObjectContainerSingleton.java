package com.eris4.benchdb.database.db4o;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.Configuration;
import com.eris4.benchdb.test.account.domain.AccountImpl;
import com.eris4.benchdb.test.msisdn.domain.MsisdnImpl;
import com.eris4.benchdb.test.person.domain.PersonImpl;
import com.eris4.benchdb.test.session.domain.SessionImpl;

public class ObjectContainerSingleton {
	
	private ObjectContainer db;
	private static ObjectContainerSingleton singleton;
	
	private ObjectContainerSingleton(){}

	private ObjectContainerSingleton(String filename){
		Configuration conf = Db4o.newConfiguration();
		conf.objectClass(SessionImpl.class).objectField("sessionId").indexed(true);
		conf.objectClass(PersonImpl.class).objectField("id").indexed(true);
		conf.objectClass(MsisdnImpl.class).objectField("msisdnId").indexed(true);
		conf.objectClass(AccountImpl.class).objectField("accountId").indexed(true);
		db = Db4o.openFile(conf,filename);
	}
	
	public synchronized static ObjectContainerSingleton getInstance(){
		if (singleton == null){
			singleton = new ObjectContainerSingleton(new Db4oDatabase().getFileName() + "/db4o");
		}
		return singleton;
	}
	
	public <E> ObjectSet<E> queryByExample(Object o) {
		return db.queryByExample(o);
	}

	public void store(Object o) {
		db.store(o);
	}

	public synchronized void commit() {
		db.commit();
	}

	public synchronized void shutdown() {
		db.close();
	}

}
