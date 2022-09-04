package myfirstwebsite.board.controller;

import java.time.LocalDateTime;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myfirstwebsite.board.domain.Board;
import myfirstwebsite.board.domain.Comment;
import myfirstwebsite.board.domain.Member;
import myfirstwebsite.board.service.BoardService;
import myfirstwebsite.board.service.CommentService;
import myfirstwebsite.board.service.MemberService;
import myfirstwebsite.board.web.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
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
  private final CommentService commentService;

  @GetMapping("/boards/new")
  public String boardForm(Model model, HttpServletRequest request) {
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
  public String writeBoard(@ModelAttribute BoardForm boardForm, BindingResult bindingResult,
    RedirectAttributes redirectAttributes, Model model, HttpServletRequest request) {

    //Validation Logic
    //제목, 저자, 내용 공백 미허용
    if (!StringUtils.hasText(boardForm.getTitle())) {
      bindingResult.rejectValue("title", "required");
    } else if (boardForm.getTitle().length() < 1 || boardForm.getTitle().length() > 30) {   //제목 길이 1~30
      bindingResult.rejectValue("title", "range");
    }
    if (!StringUtils.hasText(boardForm.getContent())) {
      bindingResult.rejectValue("content", "required");
    } else if (boardForm.getContent().length() < 1 || boardForm.getContent().length() > 230) {    //게시판 내용 길이 1~230
      bindingResult.rejectValue("content", "range");
    }

    if (bindingResult.hasErrors()) {
      log.info("errors = {} ", bindingResult);
      return "boards/postBoard";
    }

    //Board Added Success Logic
    HttpSession session = request.getSession(false);
    Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER); //세션에서 닉네임 가져오기
    boardService.postBoard(boardForm, member);

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
    board.increaseView();
    model.addAttribute("board", board);
    model.addAttribute("commentForm", new CommentForm());

    //TODO
    List<Comment> comments = commentService.findComments(boardId);
    if(comments != null && !comments.isEmpty()) {
      model.addAttribute("comments", comments);
    }
    return "boards/boardDetail";
  }
}