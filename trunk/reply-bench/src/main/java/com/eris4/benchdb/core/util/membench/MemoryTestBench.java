package com.eris4.benchdb.core.util.membench;

public class MemoryTestBench {
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		MemoryTestBench bench = new MemoryTestBench();
//		bench.showMemoryUsage(new ClassObjectFactory(Object.class));
		bench.showMemoryUsage(new TestResultObjectFactory());
	}
	
	public long calculateMemoryUsage(ObjectFactory factory) throws InstantiationException, IllegalAccessException {
	    @SuppressWarnings("unused")
		Object handle = factory.makeObject();
	    long mem0 = Runtime.getRuntime().totalMemory() -
	      Runtime.getRuntime().freeMemory();
	    long mem1 = Runtime.getRuntime().totalMemory() -
	      Runtime.getRuntime().freeMemory();
	    handle = null;
	    System.gc(); System.gc(); System.gc(); System.gc();
	    System.gc(); System.gc(); System.gc(); System.gc();
	    System.gc(); System.gc(); System.gc(); System.gc();
	    System.gc(); System.gc(); System.gc(); System.gc();
	    mem0 = Runtime.getRuntime().totalMemory() -
	      Runtime.getRuntime().freeMemory();
	    handle = factory.makeObject();
	    System.gc(); System.gc(); System.gc(); System.gc();
	    System.gc(); System.gc(); System.gc(); System.gc();
	    System.gc(); System.gc(); System.gc(); System.gc();
	    System.gc(); System.gc(); System.gc(); System.gc();
	    mem1 = Runtime.getRuntime().totalMemory() -
	      Runtime.getRuntime().freeMemory();
	    return mem1 - mem0;
	  }
	  public void showMemoryUsage(ObjectFactory factory) throws InstantiationException, IllegalAccessException {
		long mem = calculateMemoryUsage(factory);
		System.out.println(factory.getClass().getName() + " produced "
				+ factory.makeObject().getClass().getName() + " which took "
				+ mem + " bytes");
	}

}
