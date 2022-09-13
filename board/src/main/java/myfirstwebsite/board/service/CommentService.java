package myfirstwebsite.board.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import myfirstwebsite.board.controller.CommentForm;
import myfirstwebsite.board.domain.Board;
import myfirstwebsite.board.domain.Comment;
import myfirstwebsite.board.domain.Member;
import myfirstwebsite.board.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;

  @Transactional
  public Long postComment(Member member, Board board, CommentForm commentForm) {
    Comment comment = Comment.createComment(member, board, commentForm);
    commentRepository.save(comment);
    return comment.getId();
  }

  public Comment findOne(Long commentId) {
    return commentRepository.findOne(commentId);
  }

  public List<Comment> findComments(Long boardId) {
    return commentRepository.findAll(boardId);
  }

  @Transactional
  public void deleteWithBoard(Long boardId) {
    commentRepository.deleteWithBoard(boardId);
  }

  //TODO 댓글 삭제
  @Transactional
  public void delete(Long commentId) {
    commentRepository.delete(commentId);
  }
}
