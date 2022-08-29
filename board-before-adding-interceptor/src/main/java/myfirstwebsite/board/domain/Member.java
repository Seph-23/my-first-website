package myfirstwebsite.board.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Member implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_id")
  private Long id;

  @Column(nullable = false, length=30, unique = true)
  private String userId;

  @Column(nullable = false, length=100)
  private String password;

  @Column(nullable = false)
  private String userName;

  @Enumerated(EnumType.STRING)
  private Role role;
}