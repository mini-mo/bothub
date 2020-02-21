package com.gxk.bothub.porter.adapter.handler.bot;

import com.gxk.bothub.domain.Handler;
import com.gxk.bothub.domain.Hub;
import com.gxk.bothub.domain.TextContent;
import com.gxk.bothub.domain.Content;
import com.gxk.bothub.domain.ImTo;
import com.gxk.bothub.domain.Input;
import com.gxk.bothub.domain.To;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBot.Builder;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.MessageEntity;
import com.pengrad.telegrambot.model.MessageEntity.Type;
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

    // Register for updates
//    bot.setUpdatesListener(updates -> {
//      for (Update update : updates) {
//        Message message = update.message();
//        boolean mention = isMention(message);
//        if (!mention) {
//          continue;
//        }
//        Chat chat = message.chat();
//        long chatId = chat.id();
//        SendResponse response = bot.execute(new SendMessage(chatId, "Hello!"));
//      }
    // return id of last processed update or confirm them all
//      return UpdatesListener.CONFIRMED_UPDATES_ALL;
//    });
  }

  @Override
  public void handle(Input input) {
    Content content = input.getContent();
    if (content instanceof TextContent) {
      String text = ((TextContent) content).getTitle() + "\n" + String
          .join("\n", ((TextContent) content).getLines());

      To to = input.getTo();
      String chatId = null;
      if (to instanceof ImTo) {
        chatId = ((ImTo) to).getChatId();
      }
      bot.execute(new SendMessage(chatId, text));
    }
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

  @Override
  public String name() {
    return "telegram";
  }

  @Override
  public boolean support(String action) {
    return Objects.equals(action, "send");
  }
}
