package myfirstwebsite.board.domain;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Board {

  @Id @GeneratedValue
  private Long id;

  private String title;
  private String content;
  private String author;
  private int views;

  private LocalDateTime createdDate = LocalDateTime.now();    //게시글 등록일
  private LocalDateTime modifiedDate;      //게시글 수정일
}
