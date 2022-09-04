package myfirstwebsite.board.repository;

import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import myfirstwebsite.board.domain.Board;
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
    return em.createQuery("select c from Comment c where c.board.id = " + boardId
        , Comment.class)
      .getResultList();
  }

  public Comment findOne(Long id) {
    return em.find(Comment.class, id);
  }
}
