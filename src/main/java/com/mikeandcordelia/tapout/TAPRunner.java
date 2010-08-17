package com.mikeandcordelia.tapout;

import org.junit.runner.JUnitCore;

public class TAPRunner {

  public TAPRunner(){
  }

  public static void main(String[] args){
    JUnitCore core = new JUnitCore();
    core.addListener(new TAPListener());
    Class<?>[] classes = new Class[args.length];
    ClassLoader loader = ClassLoader.getSystemClassLoader();
    try {
      for(int i = 0; i < args.length; i++){
        classes[i] = loader.loadClass(args[i]);
      }
      core.run(classes);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

}

