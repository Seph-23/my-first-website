package myfirstwebsite.board.controller;

import lombok.RequiredArgsConstructor;
import myfirstwebsite.board.service.BoardService;
import myfirstwebsite.board.service.CommentService;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CommentController {

  private final BoardService boardService;
  private final CommentService commentService;

}
