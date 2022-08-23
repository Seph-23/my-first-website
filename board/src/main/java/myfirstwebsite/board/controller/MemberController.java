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
  public String signUp(@ModelAttribute MemberForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
    /**
     * Validation Logic
     */
    //아이디, 패스워드, 닉네임 공백 미허용
    if (!StringUtils.hasText(form.getUserId())) {
      bindingResult.addError(new FieldError("form", "userId", "아이디를 입력해주세요."));
    } else if (form.getUserId().length() < 8 || form.getUserId().length() > 15) { //아이디 길이는 8 ~ 15 자.
      bindingResult.addError(new FieldError("form", "userId", "아이디는 8 ~ 15 자 이내여야 합니다."));
    }
    if (!StringUtils.hasText(form.getPassword())) {
      bindingResult.addError(new FieldError("form", "password", "패스워드를 입력해주세요."));
    } else if (form.getPassword().length() < 8 || form.getPassword().length() > 20) { //패스워드 길이는 8 ~ 20 자.
      bindingResult.addError(new FieldError("form", "password", "패스워드는 8 ~ 20 자 이내여야 합니다."));
    }
    if (!StringUtils.hasText(form.getUserName())) {
      bindingResult.addError(new FieldError("form", "userName", "닉네임을 입력해주세요."));
    } else if (form.getUserName().length() < 2 || form.getUserName().length() > 10) {
      bindingResult.addError(new FieldError("form", "userName", "닉네임은 2 ~ 10 자 이내여야 합니다."));
    }

    if (bindingResult.hasErrors()) {
      log.info("errors = {} ", bindingResult);
      return "members/signUp";
    }

    /**
     * Sign-Up Success Logic
     */
    Member member = new Member();
    member.setUserId(form.getUserId());
    member.setPassword(form.getPassword());
    member.setUserName(form.getUserName());
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
