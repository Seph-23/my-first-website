package myfirstwebsite.board.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import myfirstwebsite.board.domain.Member;
import myfirstwebsite.board.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  /**
   * 회원가입!
   */
  @Transactional
  public Long signUp(Member member) {
    memberRepository.save(member);
    return member.getId();
  }

  /**
   * 회원 전체 조회!
   */
  public List<Member> findAllMembers(){
    return memberRepository.findAll();
  }

}
