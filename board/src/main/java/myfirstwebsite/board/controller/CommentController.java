package myfirstwebsite.board.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import myfirstwebsite.board.domain.Board;
import myfirstwebsite.board.domain.Comment;
import myfirstwebsite.board.domain.Member;
import myfirstwebsite.board.service.BoardService;
import myfirstwebsite.board.service.CommentService;
import myfirstwebsite.board.web.SessionConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CommentController {

  private final BoardService boardService;
  private final CommentService commentService;

  @PostMapping("/boards/{boardId}/comments")
  public String addComment(@ModelAttribute CommentForm commentForm,
    @PathVariable("boardId") Long boardId, Model model, HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
    Board board = boardService.findOne(boardId);
    commentForm.setAuthor(member.getUserName());
    commentService.postComment(member, board, commentForm);

    model.addAttribute("board", board);
    model.addAttribute("commentForm", new CommentForm());
    List<Comment> comments = commentService.findComments(boardId);
    if (comments != null && !comments.isEmpty()) {
      model.addAttribute("comments", comments);
    }
    return "boards/boardDetail";
  }
}
