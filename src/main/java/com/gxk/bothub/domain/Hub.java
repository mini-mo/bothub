package com.gxk.bothub.domain;

import com.gxk.bothub.porter.adapter.processor.DefaultProcessor;

public class Hub {

  private static final Processor processor = new DefaultProcessor();

  public static void process(Input input) {
    processor.process(input);
  }

  public static void addHandler(Handler handler) {
    boolean ret = processor.addHandler(handler);
    if (!ret) {
      // log it
    }
  }
}
