package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write")//localhost:8090/board/write
    public String boardWriteForm() {
        return "boardwrite";
    }

    @PostMapping("/board/writepro")
    public String boardWritePro(Board board) {      // 원래는 title, content로 매개변수 지정해줘야하는데, board라는 엔티티로 받아올 수 있음

        // lombok을 이용하면 getTitle을 사용할 수 있음 (get으로 가져올 수 있음)
        //System.out.println(board.getTitle());

        boardService.write(board);

        return "";
    }

    @GetMapping("/board/list")
    public String boardList(Model model) {

        model.addAttribute("list", boardService.boardList());

        return "boardlist";
    }

    // localhost:8090/board/view?id=1 이런식으로..
    @GetMapping("/board/view")
    public String boardView(Model model, Integer id) {

        // model이라는 이름으로 인티저 id값을 넘김
        model.addAttribute("board", boardService.boardView(id));

        return "boardview";
    }

}
