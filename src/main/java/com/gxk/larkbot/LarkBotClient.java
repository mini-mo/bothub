package com.gxk.larkbot;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LarkBotClient {

  private OkHttpClient okHttpClient;
  private Gson gson;
  private LarkToken larkToken;

  public LarkBotClient() {
    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    builder.addInterceptor(chain -> {
      String accessToken = AccessTokenHolder.getAccessToken();
      if (accessToken == null) {
        LarkAccessToken token = getAccessToken();
        if (token == null) {
          throw new IllegalStateException();
        }
        AccessTokenHolder.setToken(token);
        accessToken = token.getTenantAccessToken();
      }
      Request newReq = chain.request().newBuilder()
          .addHeader("Authorization", "Bearer " + accessToken)
          .build();

      return chain.proceed(newReq);
    });
    okHttpClient = builder.build();

    gson = new Gson();
    larkToken = new LarkToken("cli_9eda74c0b173d00d", "dahWTDn40E1gvhAeiCtOiHwCZOohuZV5");
  }

  public void sendTextMsg() throws IOException {
    Map<String, Object> body = new HashMap<>();
    body.put("chat_id", "oc_51ea0e54ad8785588f62a8278246fd62");
    body.put("msg_type", "text");
    Map<String, Object> content = new HashMap<>();
    content.put("text", "hello");
    body.put("content", content);

    String bb = gson.toJson(body);
    Request request = new Request.Builder()
        .url("https://open.feishu.cn/open-apis/message/v4/send/")
        .post(RequestBody.create(MediaType.get("application/json"), bb))
        .build();
    Response response = okHttpClient.newCall(request).execute();
    System.out.println(response.body().string());
  }

  public LarkAccessToken getAccessToken() throws IOException {
    Request request = new Request.Builder()
        .url("https://open.feishu.cn/open-apis/auth/v3/tenant_access_token/internal/")
        .post(RequestBody.create(MediaType.get("application/json"), gson.toJson(larkToken)))
        .build();
    Response response = new OkHttpClient().newCall(request).execute();

    return gson.fromJson(response.body().string(), LarkAccessToken.class);
  }

  public static void main(String[] args) throws IOException {
    LarkBotClient lark = new LarkBotClient();
    lark.sendTextMsg();
  }
}
