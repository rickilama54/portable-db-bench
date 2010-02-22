package com.eris4.benchdb.core.util.membench;

public interface ObjectFactory {
	public Object makeObject() throws InstantiationException, IllegalAccessException;
}