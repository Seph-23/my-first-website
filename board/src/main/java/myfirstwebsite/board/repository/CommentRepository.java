package myfirstwebsite.board.repository;

import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import myfirstwebsite.board.domain.Comment;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

  private final EntityManager em;

  public void save(Comment comment) {
    em.persist(comment);
  }

  public Comment findComment(Long id) {
    return em.find(Comment.class, id);
  }

  public List<Comment> findAll() {
    return em.createQuery("select c from Comment c", Comment.class)
      .getResultList();
  }

  public Comment findOne(Long id) {
    return em.find(Comment.class, id);
  }
}
