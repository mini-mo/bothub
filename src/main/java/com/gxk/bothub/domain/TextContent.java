package com.gxk.bothub.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TextContent implements Content {

  private final String title;

  private final List<String> lines;
}
