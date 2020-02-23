package com.gxk.bothub.porter.adapter.view.web;

import com.gxk.bothub.domain.Content;
import com.gxk.bothub.domain.From;
import com.gxk.bothub.domain.HttpFrom;
import com.gxk.bothub.domain.Hub;
import com.gxk.bothub.domain.Input;
import com.gxk.bothub.domain.TextContent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("messages")
@RestController
public class MessageApi {

  @PostMapping("")
  public void newMessage(@RequestBody Message message) {
    // message => input
    From from = new HttpFrom(message.getChannel(), message.getChatId(), Hub.toJson(message));
    Content content = new TextContent(message.getTitle(), message.getLines());
    Input input = new Input(from, "send", content);

    Hub.process(input);
  }
}
