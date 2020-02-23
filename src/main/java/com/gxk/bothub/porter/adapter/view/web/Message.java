package com.gxk.bothub.porter.adapter.view.web;

import java.util.List;
import lombok.Data;

@Data
public class Message {

  private String title;
  private List<String> lines;

  private String channel;
  private String chatId;
}
