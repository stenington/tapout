package com.mikeandcordelia.tapout;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TestFinder {

	private String searchDir;

	public TestFinder(String spec) {
		searchDir = spec.replaceAll("\\.", "/").replaceAll("\\*", "");
	}

	public List<Class<?>> find() {
		URL path = ClassLoader.getSystemResource(searchDir);
		File dir = new File(path.getFile());
		return findIn(dir);
	}
	
	private List<Class<?>> findIn(File dir) {
		File[] files = dir.listFiles();
		List<Class<?>> classes = new ArrayList<Class<?>>();
		try {
			for (int i = 0; i < files.length; i++) {
				File f = files[i];
				if( f.isFile() && f.getName().matches(".*Test\\.class")){
					classes.add(Class.forName(pathToClassName(f.getPath())));
				}
				else if( f.isDirectory() ){
					classes.addAll(findIn(f));
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return classes;
	}
	
	private String pathToClassName(String path){
		String className = path;
		className = className.substring(className.indexOf(searchDir));
		className = className.replaceAll("\\/", ".").replaceAll("\\.class", "");
		return className;
	}
}
