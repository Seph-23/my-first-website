package myfirstwebsite.board.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Board {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "board_id")
  private Long id;

  private String title;
  private String content;
  private String author;

  @Column(columnDefinition = "integer default 0", nullable = false)
  private int views;

  private LocalDateTime createdDate;    //게시글 등록일
  private LocalDateTime modifiedDate;      //게시글 수정일

  //조회수 증가
  public void increaseView() {
    views++;
  }
}