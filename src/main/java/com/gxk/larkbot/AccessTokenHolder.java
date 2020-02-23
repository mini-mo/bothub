package com.gxk.larkbot;

public abstract class AccessTokenHolder {

  private static LarkAccessToken token;

  public static void setToken(LarkAccessToken larkAccessToken) {
    token = larkAccessToken;
  }

  public static String getAccessToken() {
    if (token == null || token.isExpired()) {
      return null;
    }
    return token.getTenantAccessToken();
  }
}
