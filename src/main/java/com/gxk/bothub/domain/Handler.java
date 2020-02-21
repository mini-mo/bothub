package com.gxk.bothub.domain;

public interface Handler {

  String name();

  boolean support(String action);

  void handle(Input input);
}
