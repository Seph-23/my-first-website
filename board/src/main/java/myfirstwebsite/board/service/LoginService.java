package myfirstwebsite.board.service;

import lombok.RequiredArgsConstructor;
import myfirstwebsite.board.domain.Member;
import myfirstwebsite.board.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

  private final MemberRepository memberRepository;

  //return 이 null일 경우 로그인 실패
  public Member login(String loginId, String password) {
    return memberRepository.findByUserId(loginId)
      .filter(m -> m.getPassword().equals(password)).orElse(null);
  }

}
