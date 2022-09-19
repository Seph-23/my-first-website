package myfirstwebsite.board.repository;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import myfirstwebsite.board.controller.CommentForm;
import myfirstwebsite.board.domain.Comment;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

  private final EntityManager em;

  public void save(Comment comment) {
    em.persist(comment);
  }

  public List<Comment> findAll(@Param("boardId") Long boardId) {
    return em.createQuery("select c from Comment c where c.board.id = " + boardId,
        Comment.class).getResultList();
  }

  public Comment findOne(Long id) {
    return em.find(Comment.class, id);
  }

  public void deleteWithBoard(Long boardId) {
    List<Comment> comments = findAll(boardId);
    for (Comment comment : comments) {
      em.remove(comment);
    }
  }

  //TODO 댓글 삭제
  public void delete(Long commentId) {
    em.remove(findOne(commentId));
  }

  public void update(Long commentId, CommentForm commentForm) {
    Comment comment = findOne(commentId);
    comment.updateComment(comment, commentForm);
  }
}
