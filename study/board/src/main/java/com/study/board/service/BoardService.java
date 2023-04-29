package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    @Autowired // 의존성 주입
    private BoardRepository  boardRepository;

    // 글 작성 처리
    public void write(Board board) {

        boardRepository.save(board);    // sava(Entity)
    }

    // 게시물 리스트 조회
    public List<Board> boardList() {

        return boardRepository.findAll();
    }

    // 특정 게시글 불러오기
    public Board boardView(Integer id) {

        // findById는 int값을 받아오기때문에 매개변수로 인티저 값을 받아들임.
        return boardRepository.findById(id).get();
    }

}
