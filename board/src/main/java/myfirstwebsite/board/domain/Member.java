package myfirstwebsite.board.domain;

import static javax.persistence.FetchType.LAZY;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.ToString;
import myfirstwebsite.board.controller.MemberForm;

@Entity
@Data
public class Member implements Serializable {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_id")
  private Long id;

  @Column(nullable = false, unique = true)
  private String userId;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false, unique = true)
  private String userName;

  @Enumerated(EnumType.STRING)
  private Role role;

  @OneToMany(mappedBy = "member", fetch = LAZY)
  @ToString.Exclude
  private List<Board> board = new ArrayList<>();

  //생성 메서드
  public static Member createMember(MemberForm memberForm) {
    Member member = new Member();
    member.setUserId(memberForm.getUserId());
    member.setPassword(memberForm.getPassword());
    member.setUserName(memberForm.getUserName());
    member.setRole(Role.USER);
    return member;
  }
}