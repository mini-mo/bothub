package com.gxk.bothub.porter.adapter.processor;

import com.gxk.bothub.domain.From;
import com.gxk.bothub.domain.Handler;
import com.gxk.bothub.domain.HttpFrom;
import com.gxk.bothub.domain.ImFrom;
import com.gxk.bothub.domain.Input;
import com.gxk.bothub.domain.Processor;
import java.util.HashMap;
import java.util.Map;

public class DefaultProcessor implements Processor {

  private Map<String, Handler> handlers = new HashMap<>();

  @Override
  public void process(Input input) {
    Handler handler = getHandler(input);
    if (handler == null) {
      throw new IllegalStateException();
    }
    handler.handle(input);
  }

  private Handler getHandler(Input input) {
    Handler handler = handlers.get((input.getFrom().getName()));
    if (handler == null || !handler.support(input.getAction())) {
      return null;
    }
    return handler;
  }

  public boolean addHandler(Handler handler) {
    if (handlers.containsKey(handler.name())) {
      return false;
    }
    handlers.put(handler.name(), handler);
    return true;
  }
}
