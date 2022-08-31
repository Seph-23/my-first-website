package myfirstwebsite.board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

  @GetMapping("/home")
  public String homePage(Model model, HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session != null) {  //로그인 안됐을때 문제가 생김..
      Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
      model.addAttribute("member", member);
    }
    return "home";
  }
}