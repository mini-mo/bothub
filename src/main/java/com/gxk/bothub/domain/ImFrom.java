package com.gxk.bothub.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImFrom implements From {

  private String name;
  private String chatId;
  private String rawJson;

  @Override
  public String getRaw() {
    return rawJson;
  }
}
