package myfirstwebsite.board.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import myfirstwebsite.board.domain.Board;
import myfirstwebsite.board.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class BoardController {

  private final BoardService boardService;

  //URL Mapping
  @GetMapping("/boards/new")
  public String boardForm(Model model) {
    model.addAttribute("boardForm", new BoardForm());
    return "boards/postBoard";
  }

  @PostMapping("/boards/new")
  public String writeBoard(@Valid BoardForm form) {
    Board board = new Board();
    board.setTitle(form.getTitle());
    board.setAuthor(form.getAuthor());
    board.setContent(form.getContent());
    board.setViews(0);

    boardService.postBoard(board);

    return "redirect:/";
  }
}
