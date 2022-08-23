package myfirstwebsite.board.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myfirstwebsite.board.domain.Board;
import myfirstwebsite.board.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
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
  public String writeBoard(@ModelAttribute BoardForm boardForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
    //Validation Logic
    //제목, 저자, 내용 공백 미허용
    if (!StringUtils.hasText(boardForm.getTitle())) {
      bindingResult.rejectValue("title", "required");
    } else if (boardForm.getTitle().length() < 1 || boardForm.getTitle().length() > 30) {   //제목 길이 1~30
      bindingResult.rejectValue("title", "range");
    }
    if (!StringUtils.hasText(boardForm.getAuthor())) {
      bindingResult.rejectValue("author", "required");
    } else if (boardForm.getAuthor().length() < 2 || boardForm.getAuthor().length() > 10) {   //저자 길이 2~10
      bindingResult.rejectValue("author", "range");
    }
    if (!StringUtils.hasText(boardForm.getContent())) {
      bindingResult.rejectValue("content", "required");
    } else if (boardForm.getContent().length() < 1 || boardForm.getContent().length() > 230) {
      bindingResult.rejectValue("content", "range");
    }

    if (bindingResult.hasErrors()) {
      log.info("errors = {} ", bindingResult);
      return "boards/postBoard";
    }

    //Board Added Success Logic
    Board board = new Board();
    board.setTitle(boardForm.getTitle());
    board.setAuthor(boardForm.getAuthor());
    board.setContent(boardForm.getContent());

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