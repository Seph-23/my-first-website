package myfirstwebsite.board.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import myfirstwebsite.board.domain.Member;
import myfirstwebsite.board.domain.Role;
import myfirstwebsite.board.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
  public String signUp(@Valid MemberForm form) {
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
