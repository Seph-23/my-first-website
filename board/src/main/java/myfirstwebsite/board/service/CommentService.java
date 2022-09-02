package myfirstwebsite.board.service;

import lombok.RequiredArgsConstructor;
import myfirstwebsite.board.controller.CommentForm;
import myfirstwebsite.board.domain.Board;
import myfirstwebsite.board.domain.Comment;
import myfirstwebsite.board.domain.Member;
import myfirstwebsite.board.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;

  public Long postComment(Member member, Board board, CommentForm commentForm) {
    Comment comment = Comment.createComment(member, board, commentForm);
    commentRepository.save(comment);
    return comment.getId();
  }
}
