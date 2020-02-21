package com.gxk.bothub.domain;

public interface Processor {

  void process(Input input);

  boolean addHandler(Handler handler);
}
