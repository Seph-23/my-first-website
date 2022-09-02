package myfirstwebsite.board.domain;

import static javax.persistence.FetchType.LAZY;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;
import myfirstwebsite.board.controller.BoardForm;

@Entity
@Data
public class Board implements Serializable {

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

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  //연관관계 편의 메서드
  public void setMember(Member member) {
    this.member = member;
    member.getBoard().add(this);
  }

  //생성 메서드
  public static Board createBoard(BoardForm boardForm, Member member) {
    Board board = new Board();
    board.setMember(member);
    board.setTitle(boardForm.getTitle());
    board.setAuthor(member.getUserName());
    board.setContent(boardForm.getContent());
    board.setCreatedDate(LocalDateTime.now());
    return board;
  }

  //조회수 증가
  public void increaseView() {
    views++;
  }
}