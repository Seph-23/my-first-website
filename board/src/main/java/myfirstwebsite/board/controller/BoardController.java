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
  public String writeBoard(@ModelAttribute BoardForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
    //Validation Logic
    //제목, 저자, 내용 공백 미허용
    if (!StringUtils.hasText(form.getTitle())) {
      bindingResult.addError(new FieldError("form", "title", form.getTitle(),
                false, null, null, "제목을 입력해주세요."));
    } else if (form.getTitle().length() < 1 || form.getTitle().length() > 30) {   //제목 길이 1~30
      bindingResult.addError(new FieldError("form", "title", form.getTitle(),
                            false, null, null, "제목 길이는 1 ~ 30 자 이내여야 합니다."));
    }
    if (!StringUtils.hasText(form.getAuthor())) {
      bindingResult.addError(new FieldError("form", "author", form.getAuthor(),
                            false, null, null, "저자를 입력해주세요."));
    } else if (form.getAuthor().length() < 2 || form.getAuthor().length() > 10) {   //저자 길이 2~10
      bindingResult.addError(new FieldError("form", "author", form.getAuthor(),
                            false, null, null, "저자 길이는 2 ~ 10 자 이내여야 합니다."));
    }
    if (!StringUtils.hasText(form.getContent())) {
      bindingResult.addError(new FieldError("form", "content", form.getContent(),
                            false, null, null, "내용을 입력해주세요."));
    } else if (form.getContent().length() < 1 || form.getContent().length() > 230) {
      bindingResult.addError(new FieldError("form", "content", form.getContent(),
                            false, null, null, "내용 길이는 1 ~ 230 자 이내여야 합니다."));
    }

    if (bindingResult.hasErrors()) {
      log.info("errors = {} ", bindingResult);
      return "boards/postBoard";
    }

    //Board Added Success Logic
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