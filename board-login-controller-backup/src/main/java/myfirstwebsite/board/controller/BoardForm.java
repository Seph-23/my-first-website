package myfirstwebsite.board.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BoardForm {

  private String title;
  private String content;
  private String author;
}
