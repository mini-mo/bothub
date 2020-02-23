package com.gxk.bothub.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class HttpFrom implements From {

  private final String toName;
  private final String toChatId;
  private final String rawJson;

  @Override
  public String getName() {
    return toName;
  }

  @Override
  public String getChatId() {
    return toChatId;
  }

  @Override
  public String getRaw() {
    return rawJson;
  }
}
