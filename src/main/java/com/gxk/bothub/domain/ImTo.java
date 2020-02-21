package com.gxk.bothub.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ImTo implements To {

  private final String name;
  private final String chatId;
}
