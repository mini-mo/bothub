package com.gxk.larkbot;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LarkToken {

  @SerializedName("app_id")
  private String appId;
  @SerializedName("app_secret")
  private String appSecret;
}
