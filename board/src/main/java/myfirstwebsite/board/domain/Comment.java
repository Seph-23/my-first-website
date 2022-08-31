package myfirstwebsite.board.domain;

import static javax.persistence.FetchType.LAZY;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Comment {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "comment_id")
  private Long id;

  private String content;
  private String author;
  private LocalDateTime createdDate;    //커멘트 등록일
  private LocalDateTime modifiedDate;   //커멘트 수정일

  @ManyToOne(fetch = LAZY)    //게시글과 연관관계 매핑
  @JoinColumn(name = "board_id")
  private Board board;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "member_id")
  private Member member;
}
