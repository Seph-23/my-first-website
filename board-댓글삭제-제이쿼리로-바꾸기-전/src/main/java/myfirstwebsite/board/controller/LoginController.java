package myfirstwebsite.board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myfirstwebsite.board.domain.Member;
import myfirstwebsite.board.service.LoginService;
import myfirstwebsite.board.web.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

  private final LoginService loginService;

  @GetMapping("/members/login")
  public String loginForm(@ModelAttribute LoginForm form, HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session == null) {    //로그아웃 하자마자 로그인 할때 에러 방지.
      return "login/loginForm";
    }
    Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
    if (member != null) {     //로그인 된 상태에서는 로그인 못하게.
      return "home";
    }
    return "login/loginForm";
  }

  @PostMapping("/members/login")
  public String login(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult,
    @RequestParam(defaultValue = "/") String redirectURL, HttpServletRequest request) {

    if (bindingResult.hasErrors()) {
      return "login/loginForm";
    }

    Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());

    if (loginMember == null) {
      bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
      return "login/loginForm";
    }

    //로그인 할때 세션에 로그인 한 유저 객체 저장
    HttpSession session = request.getSession();
    session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

    return "redirect:" + redirectURL;
  }

  @PostMapping("/logout")
  public String logout(HttpServletRequest request) {
    HttpSession session = request.getSession(false);

    if (session != null) {
      session.invalidate();
    }
    return "redirect:/";
  }
}
