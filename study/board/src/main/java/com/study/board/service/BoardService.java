package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired // 의존성 주입
    private BoardRepository  boardRepository;

    // 글 작성 처리
    public void write(Board board, MultipartFile file) throws Exception{

        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        UUID uuid = UUID.randomUUID();

        String fileName = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile);

        board.setFilename(fileName);
        board.setFilepath("/files/" + fileName);

        boardRepository.save(board);    // sava(Entity)
    }

    // 게시물 리스트 조회
    public Page<Board> boardList(Pageable pageable) {

        // 컨트롤러에서 pageable을 가져와야하기 때문에 1. 매개변수에 pageable을 넣고, 2. findAll(pageable)을 넣게되면서 기존 public List<> 에서 public Page<>로 변경
        // findAll > DB에 있는 정보를 모두 가져옴
        return boardRepository.findAll(pageable);
    }

    // 특정 게시글 불러오기
    public Board boardView(Integer id) {

        System.out.println(id);

        // findById는 int값을 받아오기때문에 매개변수로 인티저 값을 받아들임.
        //.get없이 그냥 쓰면 optional값으로 받아 들여서 오류가 발생 >  .get을 사용하면 해결
        return boardRepository.findById(id).get();
    }


    // 특정 게시글 삭제
    public void boardDelete(Integer id) {

        boardRepository.deleteById(id);

    }
}
