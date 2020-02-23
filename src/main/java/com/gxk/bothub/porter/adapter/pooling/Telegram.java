package com.gxk.bothub.porter.adapter.pooling;

import com.google.gson.Gson;
import com.gxk.bothub.domain.Content;
import com.gxk.bothub.domain.From;
import com.gxk.bothub.domain.Hub;
import com.gxk.bothub.domain.ImFrom;
import com.gxk.bothub.domain.Input;
import com.gxk.bothub.domain.TextContent;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBot.Builder;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.MessageEntity;
import com.pengrad.telegrambot.model.MessageEntity.Type;
import com.pengrad.telegrambot.model.Update;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Component;

@Component
public class Telegram {

  private final TelegramBot bot;
  private final Gson gson;

  public Telegram() {
    String botToken = "1066789440:AAGnuE7wz05l47ZzQQ_JReZGdqusUxT3mrQ";
    bot = new Builder(botToken).okHttpClient(client("127.0.0.1", 8001)).build();
    gson = new Gson();
  }

  @PostConstruct
  public void polling() {
    bot.setUpdatesListener(updates -> {
      for (Update update : updates) {
        Message message = update.message();
        boolean mention = isMention(message);
        if (!mention) {
          continue;
        }
        Chat chat = message.chat();
        long chatId = chat.id();

        String text = message.text();
        int idx = text.indexOf(" ");
        if (idx < 0) {
          text = "你啥都不说啥意思?";
        } else {
          text = text.substring(idx).trim();
        }
        text = text.replace("吗", "");
        text = text.replace("?", "!");
        text = text.replace("？", "!");
        if (text.equals("妹子图")) {
          text = "此处应有妹子图";
        }

        From from = new ImFrom("telegram", String.valueOf(chatId), gson.toJson(update));
        Content content = new TextContent(text, new ArrayList<>());
        Input input = new Input(from, "send", content);
        Hub.process(input);
      }
      return UpdatesListener.CONFIRMED_UPDATES_ALL;
    });
  }

  private static boolean isMention(Message message) {
    if (message == null || message.entities() == null || message.entities().length == 0) {
      return false;
    }

    for (MessageEntity entity : message.entities()) {
      if (entity.type().equals(Type.mention)) {
        return true;
      }
    }
    return false;
  }

  private static OkHttpClient client(final String proxyHost, final int proxyPort) {
    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    builder.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort)))
        .retryOnConnectionFailure(true);
    return builder.build();
  }
}
