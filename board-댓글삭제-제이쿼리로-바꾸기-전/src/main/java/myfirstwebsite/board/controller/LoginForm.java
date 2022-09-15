package myfirstwebsite.board.controller;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginForm implements Serializable {

  @NotEmpty
  private String loginId;
  @NotEmpty
  private String password;

}
