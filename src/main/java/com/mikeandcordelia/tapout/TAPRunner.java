package com.mikeandcordelia.tapout;

import java.util.List;

import org.junit.runner.JUnitCore;

public class TAPRunner {
	public static void main(String[] args) {
		if (args.length < 1) {
			throw new Error("\n(>.>) Tell me more... (I need an argument.)");
		}
		String testArg = args[0];
		if (testArg.matches("^[a-zA-Z0-9.]+\\.\\*$")) {
			runFromSpec(testArg);
		} 
		else if (testArg.matches("^[a-zA-Z0-9.]+(\\.class)?$")) {
			runClassFile(testArg.replaceAll("\\.class$", ""));
		}
		else {
			throw new Error("\n(?_?) I'm not sure what to do with this: " + testArg);
		}
	}

	private static void runFromSpec(String testSpec) {
		JUnitCore core = new JUnitCore();
		core.addListener(new TAPListener());
		TestFinder finder = new TestFinder(testSpec);
		List<Class<?>> testClasses = finder.find();
		for (Class<?> testClass : testClasses) {
			try {
				core.run(testClass);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static void runClassFile(String className) {
		JUnitCore core = new JUnitCore();
		core.addListener(new TAPListener());
		try {
			core.run(Class.forName(className));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
