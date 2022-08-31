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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
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
  public String signUpForm(Model model, HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session != null) {    //로그인 되어있으면 다시 홈으로
      return "home";
    }
    model.addAttribute("memberForm", new MemberForm());
    return "members/signUp";
  }

  @PostMapping("/members/new")
  public String signUp(@ModelAttribute MemberForm memberForm, BindingResult bindingResult,
    RedirectAttributes redirectAttributes, Model model) {
    //Validation Logic
    if (!StringUtils.hasText(memberForm.getUserId())) {   //아이디 공백 미허용
      bindingResult.rejectValue("userId", "required");
    } else if (memberForm.getUserId().length() < 8 || memberForm.getUserId().length() > 15) { //아이디 길이는 8 ~ 15 자.
      bindingResult.rejectValue("userId", "range");
    }
    //비밀번호 정규식 (8~20자, 특문+숫자+영문)
    Pattern passPattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$");
    Matcher passMatcher = passPattern.matcher(memberForm.getPassword());
    if (!StringUtils.hasText(memberForm.getPassword())) {     //비밀번호 공백 미허용
      bindingResult.rejectValue("password", "required");
    } else if (!passMatcher.find()) { //패스워드 8~20자, 특문+숫자+영문
      bindingResult.rejectValue("password", "check");
    }
    if (!StringUtils.hasText(memberForm.getUserName())) {     //닉네임 공백 미허용
      bindingResult.rejectValue("userName", "required");
    } else if (memberForm.getUserName().length() < 2 || memberForm.getUserName().length() > 10) { //닉네임 길이는 2~10자.
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
