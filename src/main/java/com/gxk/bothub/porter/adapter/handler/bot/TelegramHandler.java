package com.gxk.bothub.porter.adapter.handler.bot;

import com.gxk.bothub.domain.Content;
import com.gxk.bothub.domain.Handler;
import com.gxk.bothub.domain.Hub;
import com.gxk.bothub.domain.Input;
import com.gxk.bothub.domain.TextContent;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBot.Builder;
import com.pengrad.telegrambot.request.SendMessage;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Objects;
import javax.annotation.PostConstruct;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Component;

@Component
public class TelegramHandler implements Handler {

  private final TelegramBot bot;

  @PostConstruct
  public void init() {
    Hub.addHandler(this);
  }

  public TelegramHandler() {
    String botToken = "1066789440:AAGnuE7wz05l47ZzQQ_JReZGdqusUxT3mrQ";
    bot = new Builder(botToken).okHttpClient(client("127.0.0.1", 8001)).build();
  }

  @Override
  public void handle(Input input) {
    Content content = input.getContent();
    if (content instanceof TextContent) {
      String text = ((TextContent) content).getTitle() + "\n" + String
          .join("\n", ((TextContent) content).getLines());

      bot.execute(new SendMessage(input.getFrom().getChatId(), text));
    }
  }

  private static OkHttpClient client(final String proxyHost, final int proxyPort) {
    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    builder.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort)))
        .retryOnConnectionFailure(true);
    return builder.build();
  }

  @Override
  public String name() {
    return "telegram";
  }

  @Override
  public boolean support(String action) {
    return Objects.equals(action, "send");
  }
}
