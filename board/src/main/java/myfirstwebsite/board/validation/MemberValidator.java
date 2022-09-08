package myfirstwebsite.board.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import myfirstwebsite.board.controller.MemberForm;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MemberValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return MemberForm.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    MemberForm memberForm = (MemberForm) target;

    //Validation Logic
    if (!StringUtils.hasText(memberForm.getUserId())) {   //아이디 공백 미허용
      errors.rejectValue("userId", "required");
    } else if (memberForm.getUserId().length() < 8 || memberForm.getUserId().length() > 15) { //아이디 길이는 8 ~ 15 자.
      errors.rejectValue("userId", "range");
    }
    //비밀번호 정규식 (8~20자, 특문+숫자+영문)
    Pattern passPattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$");
    Matcher passMatcher = passPattern.matcher(memberForm.getPassword());
    if (!StringUtils.hasText(memberForm.getPassword())) {     //비밀번호 공백 미허용
      errors.rejectValue("password", "required");
    } else if (!passMatcher.find()) { //패스워드 8~20자, 특문+숫자+영문
      errors.rejectValue("password", "check");
    }
    if (!StringUtils.hasText(memberForm.getUserName())) {     //닉네임 공백 미허용
      errors.rejectValue("userName", "required");
    } else if (memberForm.getUserName().length() < 2 || memberForm.getUserName().length() > 10) { //닉네임 길이는 2~10자.
      errors.rejectValue("userName", "range");
    }
  }
}
