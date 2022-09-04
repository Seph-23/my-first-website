package myfirstwebsite.board.validation;

import myfirstwebsite.board.controller.BoardForm;
import myfirstwebsite.board.domain.Board;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

//@Component
public class BoardValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return BoardForm.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    BoardForm boardForm = (BoardForm) target;

    //Validation Logic
    //제목, 저자, 내용 공백 미허용
    if (!StringUtils.hasText(boardForm.getTitle())) {
      errors.rejectValue("title", "required");
    } else if (boardForm.getTitle().length() < 1 || boardForm.getTitle().length() > 30) {   //제목 길이 1~30
      errors.rejectValue("title", "range");
    }
    if (!StringUtils.hasText(boardForm.getContent())) {
      errors.rejectValue("content", "required");
    } else if (boardForm.getContent().length() < 1 || boardForm.getContent().length() > 230) {    //게시판 내용 길이 1~230
      errors.rejectValue("content", "range");
    }
  }
}
