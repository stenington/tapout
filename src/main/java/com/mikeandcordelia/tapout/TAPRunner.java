package com.mikeandcordelia.tapout;

import org.junit.runner.JUnitCore;

public class TAPRunner {

	private static ClassLoader loader = ClassLoader.getSystemClassLoader();

	public static void main(String[] testSpecs) {
		JUnitCore core = new JUnitCore();
		core.addListener(new TAPListener());
		for(int i=0; i < testSpecs.length; i++){
			try {
				Class<?> testClass = loader.loadClass(testSpecs[i]);
				core.run(testClass);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
