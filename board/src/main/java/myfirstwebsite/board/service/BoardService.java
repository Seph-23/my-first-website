package myfirstwebsite.board.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import myfirstwebsite.board.controller.BoardForm;
import myfirstwebsite.board.domain.Board;
import myfirstwebsite.board.domain.Member;
import myfirstwebsite.board.repository.BoardRepository;
import myfirstwebsite.board.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

  private final BoardRepository boardRepository;
  private final MemberRepository memberRepository;

  /**
   * 게시글 등록!
   */
  @Transactional
  public Long postBoard(BoardForm boardForm, Member member) {
    Member member1 = memberRepository.findMember(member.getId());
    Board board = Board.createBoard(boardForm, member1);
    boardRepository.save(board);
    return board.getId();
  }

  public List<Board> findBoards() {
    return boardRepository.findAll();
  }

  public Board findOne(Long boardId) {
    return boardRepository.findOne(boardId);
  }
}
