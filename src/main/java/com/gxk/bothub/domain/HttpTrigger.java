package com.gxk.bothub.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class HttpTrigger implements From {

  private final String name;
}
