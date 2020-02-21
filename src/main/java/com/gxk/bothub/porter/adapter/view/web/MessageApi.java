package com.gxk.bothub.porter.adapter.view.web;

import com.gxk.bothub.domain.HttpTrigger;
import com.gxk.bothub.domain.Hub;
import com.gxk.bothub.domain.TextContent;
import com.gxk.bothub.domain.Content;
import com.gxk.bothub.domain.From;
import com.gxk.bothub.domain.ImTo;
import com.gxk.bothub.domain.Input;
import com.gxk.bothub.domain.To;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("messages")
@RestController
public class MessageApi {

  @PostMapping("/")
  public void newMessage(@RequestBody Message message) {
    // message => input
    From from = new HttpTrigger("raw http");
    Content content = new TextContent(message.getTitle(), message.getLines());
    To to = new ImTo(message.getHandler(), message.getChatId());
    Input input = new Input(from, "send", to, content);

    Hub.process(input);
  }
}
