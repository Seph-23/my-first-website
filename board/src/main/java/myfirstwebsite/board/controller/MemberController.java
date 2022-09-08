package myfirstwebsite.board.controller;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myfirstwebsite.board.domain.Member;
import myfirstwebsite.board.domain.Role;
import myfirstwebsite.board.service.MemberService;
import myfirstwebsite.board.validation.MemberValidator;
import myfirstwebsite.board.web.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;
  private final MemberValidator memberValidator;

  @InitBinder("memberForm")
  public void init(WebDataBinder dataBinder) {
    dataBinder.addValidators(memberValidator);
  }

  @GetMapping("/members/new")
  public String signUpForm(Model model, HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session == null) {    //로그아웃 하자마자 다시 회원가입 눌렀을때 에러 방지
      model.addAttribute("memberForm", new MemberForm());
      return "members/signUp";
    }
    Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
    if (member != null) {   //로그인 된 상태에서는 회원가입 못하게.
      return "home";
    }
    model.addAttribute("memberForm", new MemberForm());
    return "members/signUp";
  }

  @PostMapping("/members/new")
  public String signUp(@Validated @ModelAttribute MemberForm memberForm,
    BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

    if (bindingResult.hasErrors()) {
      log.info("errors = {} ", bindingResult);
      return "members/signUp";
    }

    //SignUp Success Logic
    memberService.signUp(memberForm);

    return "redirect:/";
  }

  @GetMapping("/members/list")
  public String showMembers(Model model){
    List<Member> members = memberService.findAllMembers();
    model.addAttribute("members", members);
    return "members/membersList";
  }
}
