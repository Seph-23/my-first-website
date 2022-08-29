package myfirstwebsite.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myfirstwebsite.board.domain.Member;
import myfirstwebsite.board.repository.MemberRepository;
import myfirstwebsite.board.web.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

//  @RequestMapping("/")
  public String home() {
    return "home";
  }

  @GetMapping("/")
  public String homeLogin(
    @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
    Model model) {

    if (loginMember == null) {
      return "home";
    }
    model.addAttribute("member", loginMember);
    return "loginHome";
  }
}