package myfirstwebsite.board.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myfirstwebsite.board.domain.Member;
import myfirstwebsite.board.domain.Role;
import myfirstwebsite.board.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;

  @GetMapping("/members/new")
  public String signUpForm(Model model) {
    model.addAttribute("memberForm", new MemberForm());
    return "members/signUp";
  }

  @PostMapping("/members/new")
  public String signUp(@ModelAttribute MemberForm memberForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
    //Validation Logic
    //아이디, 패스워드, 닉네임 공백 미허용
    if (!StringUtils.hasText(memberForm.getUserId())) {
      bindingResult.rejectValue("userId", "required");
    } else if (memberForm.getUserId().length() < 8 || memberForm.getUserId().length() > 15) { //아이디 길이는 8 ~ 15 자.
      bindingResult.rejectValue("userId", "range");
    }
    if (!StringUtils.hasText(memberForm.getPassword())) {
      bindingResult.rejectValue("password", "required");
    } else if (memberForm.getPassword().length() < 8 || memberForm.getPassword().length() > 20) { //패스워드 길이는 8 ~ 20 자.
      bindingResult.rejectValue("password", "range");
    }
    if (!StringUtils.hasText(memberForm.getUserName())) {
      bindingResult.rejectValue("userName", "required");
    } else if (memberForm.getUserName().length() < 2 || memberForm.getUserName().length() > 10) {
      bindingResult.rejectValue("userName", "range");
    }

    if (bindingResult.hasErrors()) {
      log.info("errors = {} ", bindingResult);
      return "members/signUp";
    }

    //SignUp Success Logic
    Member member = new Member();
    member.setUserId(memberForm.getUserId());
    member.setPassword(memberForm.getPassword());
    member.setUserName(memberForm.getUserName());
    member.setRole(Role.USER);

    memberService.signUp(member);

    return "redirect:/";
  }

  @GetMapping("/members/list")
  public String showMembers(Model model){
    List<Member> members = memberService.findAllMembers();
    model.addAttribute("members", members);
    return "members/membersList";
  }
}
