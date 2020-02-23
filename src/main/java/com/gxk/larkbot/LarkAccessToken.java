package com.gxk.larkbot;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LarkAccessToken {

  private Integer code;
  private String msg;
  @SerializedName("tenant_access_token")
  private String tenantAccessToken;
  private Integer expire;

  private Long now = System.currentTimeMillis();

  public boolean isExpired() {
    return (System.currentTimeMillis() - now) / 1000 - expire > 60;
  }
}
