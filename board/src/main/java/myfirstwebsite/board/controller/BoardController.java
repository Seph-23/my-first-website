package myfirstwebsite.board.controller;

import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myfirstwebsite.board.domain.Board;
import myfirstwebsite.board.domain.Comment;
import myfirstwebsite.board.domain.Member;
import myfirstwebsite.board.service.BoardService;
import myfirstwebsite.board.service.CommentService;
import myfirstwebsite.board.validation.BoardValidator;
import myfirstwebsite.board.web.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

  private final BoardService boardService;
  private final CommentService commentService;
  private final BoardValidator boardValidator;

  @InitBinder("boardForm")    //boardForm 객체만 검증하도록.
  public void init(WebDataBinder dataBinder) {
    dataBinder.addValidators(boardValidator);
  }

  @GetMapping("/boards/new")
  public String writeBoard(Model model, HttpServletRequest request) {
    model.addAttribute("boardForm", new BoardForm());

    //세션에서 회원 오브젝트 가져오기
    HttpSession session = request.getSession(false);
    if (session != null) {
      Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
      model.addAttribute("member", member);
    }

    return "boards/postBoard";
  }

  @PostMapping("/boards/new")
  public String writeBoard(@Validated @ModelAttribute BoardForm boardForm,
    BindingResult bindingResult, RedirectAttributes redirectAttributes,
    Model model, HttpServletRequest request) {

    //세션에서 로그인 유저 객체 가져오기
    HttpSession session = request.getSession(false);
    Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER); //세션에서 멤버 객체 가져오기

    if (bindingResult.hasErrors()) {
      log.info("errors = {} ", bindingResult);
      model.addAttribute(member);
      return "boards/postBoard";
    }

    //Board Added Success Logic
    boardService.postBoard(boardForm, member);

    return "redirect:/home";
  }

  @GetMapping("/boards")
  public String boardList(Model model) {
    List<Board> boards = boardService.findBoards();
    model.addAttribute("boards", boards);
    return "boards/listBoard";
  }

  @GetMapping("/boards/{boardId}")
  public String boardDetail(@PathVariable("boardId") Long boardId, Model model,
    HttpServletRequest request) {
    Board board = boardService.findOne(boardId);
    board.increaseView();
    model.addAttribute("board", board);
    model.addAttribute("commentForm", new CommentForm());

    //현재 로그인 한 유저
    HttpSession session = request.getSession(false);
    Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
    //현재 게시글을 등록한 유저
    Long memberTwoId = board.getMember().getId();
    if (Objects.equals(member.getId(), memberTwoId)) {
      model.addAttribute("member", member);
    } else {
      model.addAttribute("member", "none");
    }

    List<Comment> comments = commentService.findComments(boardId);
    if (comments != null && !comments.isEmpty()) {
      model.addAttribute("comments", comments);
    }
    return "boards/boardDetail";
  }

  @RequestMapping("/boards/{id}/delete")
  public String boardDelete(@PathVariable("id") Long boardId) {
    commentService.deleteWithBoard(boardId);
    boardService.delete(boardId);
    return "redirect:/boards";
  }

  @GetMapping("/boards/{id}/update")
  public String boardUpdate(@PathVariable("id") Long boardId, Model model) {
    Board board = boardService.findOne(boardId);
    model.addAttribute(board);
    model.addAttribute(board.getMember());
    model.addAttribute("boardForm", new BoardForm());
    return "boards/boardUpdate";
  }

  @PostMapping("/boards/{id}/update")
  public String boardUpdate(@PathVariable("id") Long boardId,
    @Validated @ModelAttribute BoardForm boardForm, BindingResult bindingResult, Model model,
    HttpServletRequest request) {

    if (bindingResult.hasErrors()) {
      log.info("errors = {} ", bindingResult);
      Board board = boardService.findOne(boardId);
      model.addAttribute(board);
      model.addAttribute(board.getMember());
      model.addAttribute(boardForm);
      return "boards/boardUpdate";
    }

    //Update
    Board board = boardService.update(boardId, boardForm);

    //After Update
    model.addAttribute(board);
    model.addAttribute("commentForm", new CommentForm());
    List<Comment> comments = commentService.findComments(boardId);
    if (comments != null && !comments.isEmpty()) {
      model.addAttribute("comments", comments);
    }
    HttpSession session = request.getSession(false);
    Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
    model.addAttribute(member);

    return "boards/boardDetail";
  }
}