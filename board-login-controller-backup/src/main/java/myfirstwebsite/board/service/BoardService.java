package myfirstwebsite.board.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import myfirstwebsite.board.domain.Board;
import myfirstwebsite.board.repository.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

  private final BoardRepository boardRepository;

  /**
   * 게시글 등록!
   */
  @Transactional
  public Long postBoard(Board board) {
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
