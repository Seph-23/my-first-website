package myfirstwebsite.board.service;

import lombok.RequiredArgsConstructor;
import myfirstwebsite.board.repository.BoardRepository;
import myfirstwebsite.board.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final BoardRepository boardRepository;
  private final CommentRepository commentRepository;

}
