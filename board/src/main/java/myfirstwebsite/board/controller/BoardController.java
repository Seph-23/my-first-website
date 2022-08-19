package myfirstwebsite.board.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import myfirstwebsite.board.domain.Board;
import myfirstwebsite.board.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class BoardController {

  private final BoardService boardService;

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

    boardService.postBoard(board);

    return "redirect:/";
  }

  @GetMapping("/boards")
  public String boardList(Model model) {
    List<Board> boards = boardService.findBoards();
    model.addAttribute("boards", boards);
    return "boards/listBoard";
  }

  @GetMapping("/boards/{boardId}")
  public String boardDetail(@PathVariable("boardId") Long boardId, Model model) {
    Board board = boardService.findOne(boardId);
    model.addAttribute("board", board);
    return "boards/boardDetail";
  }
}