package myfirstwebsite.board.repository;

import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import myfirstwebsite.board.domain.Member;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

  private final EntityManager em;

  public void save(Member member) {       //멤버 엔티티를 디비에 저장.
    em.persist(member);
  }

  public Member findMember(Long id) {
    return em.find(Member.class, id);
  }
}
