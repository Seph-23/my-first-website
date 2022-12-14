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
import myfirstwebsite.board.web.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
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

    //현재 게시글을 등록한 유저인지 재확인.
    Long memberTwoId = board.getMember().getId();
    if (Objects.equals(member.getId(), memberTwoId)) {
      model.addAttribute("member", memberTwoId);
    } else {
      model.addAttribute("member", "none");
    }

    model.addAttribute("board", board);
    model.addAttribute("commentForm", new CommentForm());
    List<Comment> comments = commentService.findComments(boardId);
    if (comments != null && !comments.isEmpty()) {
      model.addAttribute("comments", comments);
    }
    return "boards/boardDetail";
  }

  @PostMapping("/comments/{id}/delete")
  public String deleteComment(@PathVariable("id") Long commentId, Model model,
    HttpServletRequest request) {

    HttpSession session = request.getSession(false);
    Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
    Board board = boardService.findOne(commentService.findOne(commentId).getBoard().getId());
    Long commentMember = commentService.findOne(commentId).getMember().getId();

    model.addAttribute("board", board);
    model.addAttribute("commentForm", new CommentForm());

    //현재 게시글을 등록한 유저인지 재확인.
    Long memberTwoId = board.getMember().getId();
    if (Objects.equals(member.getId(), memberTwoId)) {
      model.addAttribute("member", memberTwoId);
    } else {
      model.addAttribute("member", "none");
    }

    if (Objects.equals(commentMember, member.getId())) {
      commentService.delete(commentId);
      List<Comment> comments = commentService.findComments(board.getId());
      if (comments != null && !comments.isEmpty()) {
        model.addAttribute("comments", comments);
      }
      return "boards/boardDetail";
    }

    List<Comment> comments = commentService.findComments(board.getId());
    if (comments != null && !comments.isEmpty()) {
      model.addAttribute("comments", comments);
    }

    return "boards/boardDetail";
  }

  @PostMapping("/comments/{id}/update-form")
  public String updateCommentForm(@PathVariable("id") Long commentId, Model model,
    HttpServletRequest request) {

    HttpSession session = request.getSession(false);
    Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
    Board board = boardService.findOne(commentService.findOne(commentId).getBoard().getId());
    Long commentMember = commentService.findOne(commentId).getMember().getId();

    model.addAttribute("board", board);
    model.addAttribute("commentForm", new CommentForm());

    //현재 게시글을 등록한 유저인지 재확인.
    Long memberTwoId = board.getMember().getId();
    if (Objects.equals(member.getId(), memberTwoId)) {
      model.addAttribute("member", memberTwoId);
    } else {
      model.addAttribute("member", "none");
    }

    // 수정 가능할때 수정 폼으로 이동
    if (Objects.equals(commentMember, member.getId())) {
      model.addAttribute("comment", commentService.findOne(commentId));
      return "comments/updateComment";
    }

    //수정 불가능 할때
    List<Comment> comments = commentService.findComments(board.getId());
    if (comments != null && !comments.isEmpty()) {
      model.addAttribute("comments", comments);
    }

    return "boards/boardDetail";
  }

  @PostMapping("/comments/{id}/update")
  public String updateComment(@ModelAttribute CommentForm commentForm,
    @PathVariable("id") Long commentId, Model model,
    HttpServletRequest request) {

    HttpSession session = request.getSession(false);
    Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
    Board board = boardService.findOne(commentService.findOne(commentId).getBoard().getId());
    Long commentMember = commentService.findOne(commentId).getMember().getId();

    model.addAttribute("board", board);
    model.addAttribute("commentForm", new CommentForm());

    //현재 게시글을 등록한 유저인지 재확인.
    Long memberTwoId = board.getMember().getId();
    if (Objects.equals(member.getId(), memberTwoId)) {
      model.addAttribute("member", memberTwoId);
    } else {
      model.addAttribute("member", "none");
    }

    //댓글 수정
    commentService.update(commentId, commentForm);

    List<Comment> comments = commentService.findComments(board.getId());
    if (comments != null && !comments.isEmpty()) {
      model.addAttribute("comments", comments);
    }

    return "boards/boardDetail";
  }
}
