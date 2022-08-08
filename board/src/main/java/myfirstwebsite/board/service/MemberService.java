package myfirstwebsite.board.service;

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
}
