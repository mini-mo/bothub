package com.gxk.bothub.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Input {

  private final From from;
  private final String action;
  private final Content content;
}
