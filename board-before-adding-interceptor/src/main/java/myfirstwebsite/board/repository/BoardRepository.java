package myfirstwebsite.board.repository;

import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import myfirstwebsite.board.domain.Board;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

  private final EntityManager em;

  public void save(Board board) {      //보드 엔티티를 디비에 저장.
    em.persist(board);
  }

  public Board findBoard(Long id) {
    return em.find(Board.class, id);
  }

  public List<Board> findAll() {
    return em.createQuery("select b from Board b", Board.class)
      .getResultList();
  }

  public Board findOne(Long id) {
    return em.find(Board.class, id);
  }
}
