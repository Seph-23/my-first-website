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
import myfirstwebsite.board.controller.CommentForm;

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

  //연관관계 편의 메서드
  public void setMember(Member member) {
    this.member = member;
  }

  public void setBoard(Board board) {
    this.board = board;
  }

  //생성 메서드
  public static Comment createComment(Member member, Board board, CommentForm commentForm) {
    Comment comment = new Comment();
    comment.setMember(member);
    comment.setBoard(board);
    comment.setContent(commentForm.getContent());
    comment.setAuthor(commentForm.getAuthor());
    comment.setCreatedDate(LocalDateTime.now());
    comment.setModifiedDate(LocalDateTime.now());
    return comment;
  }

  //댓글 수정 메서드
  public void updateComment(Comment comment, CommentForm commentForm) {
    comment.setContent(commentForm.getContent());
    comment.setModifiedDate(LocalDateTime.now());
  }
}
