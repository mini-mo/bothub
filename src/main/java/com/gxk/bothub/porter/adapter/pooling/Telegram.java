package com.gxk.bothub.porter.adapter.pooling;

import com.gxk.bothub.domain.Content;
import com.gxk.bothub.domain.From;
import com.gxk.bothub.domain.HttpTrigger;
import com.gxk.bothub.domain.Hub;
import com.gxk.bothub.domain.ImTo;
import com.gxk.bothub.domain.Input;
import com.gxk.bothub.domain.TextContent;
import com.gxk.bothub.domain.To;
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

  public Telegram() {
    String botToken = "1066789440:AAGnuE7wz05l47ZzQQ_JReZGdqusUxT3mrQ";
    bot = new Builder(botToken).okHttpClient(client("127.0.0.1", 8001)).build();
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
        text = text.substring(idx).trim();
        text = text.replace("吗", "");
        text = text.replace("?", "!");
        text = text.replace("？", "!");
        if (text.equals("妹子图")) {
          text = "此处应有妹子图";
        }

        From from = new HttpTrigger("telegram");
        Content content = new TextContent(text, new ArrayList<>());
        To to = new ImTo("telegram", String.valueOf(chatId));
        Input input = new Input(from, "send", to, content);
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
