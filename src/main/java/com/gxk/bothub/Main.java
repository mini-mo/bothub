package com.gxk.bothub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
  }

//  public static void main(String[] args) {
  // 通知类
  // \ input | -> core -> | executor |

  // CD send msg ->  dispatcher -> lar output with id

  // deploy pluto begin -> dispather ->

  // github issue open -> dispather -> lark output with id.

  // input plugin
  // raw http api
  // github webhook
  // cron task pull api

  // 交互式
  // raw api <-> dispatcher <-> executor
  // lark robot
  // we robot
  // dd robot
  // tg robot

  // input polling

//    DefaultProcessor processor = new DefaultProcessor();
//
//    HttpReceiver receiver = new HttpReceiver();
//    receiver.on(input -> {
//          processor.process(input);
//        }
//    );

//    receiver.mockInput(new Input(null, "msg", new TextContent()));
//  }
}
