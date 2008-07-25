package com.eris4.benchdb.core.util.membench;

public class ClassObjectFactory implements ObjectFactory{
	
	@SuppressWarnings("unchecked")
	private Class c;
	
	@SuppressWarnings("unchecked")
	public ClassObjectFactory(Class c) {
		this.c = c;
	}

	@Override
	public Object makeObject() throws InstantiationException, IllegalAccessException {
		return c.newInstance();
	}

	
}
