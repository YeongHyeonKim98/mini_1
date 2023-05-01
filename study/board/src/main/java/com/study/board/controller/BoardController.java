package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write")//localhost:8090/board/write
    public String boardWriteForm() {
        return "boardwrite";
    }

    @PostMapping("/board/writepro")
    public String boardWritePro(Board board, Model model, MultipartFile file) throws Exception {      // 원래는 title, content로 매개변수 지정해줘야하는데, board라는 엔티티로 받아올 수 있음

        // lombok을 이용하면 getTitle을 사용할 수 있음 (get으로 가져올 수 있음)
        //System.out.println(board.getTitle());

        boardService.write(board, file);


        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }

    // http://localhost:8090/board/list?page=0 이렇게 넣으면 가장 최근에 쓴 글(sort.direction.desc로인해)이 가장 위로 올라오고, size를 10이라 했으니, 10개의 글이 나옴
    // http://localhost:8090/board/list?page=5&size=20 이렇게 써서 사이즈도 직접 지정할 수 있음.
    @GetMapping("/board/list")
    public String boardList(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Board> list = boardService.boardList(pageable);

        int nowPage = list.getPageable().getPageNumber() + 1;   // 0부터 시작이기 때문에 1을 더함.
        int startPage = Math.max(nowPage - 4, 1);   // 두 수를 비교하여 높은 수 출력 > 이러면 가장 작은 값이 1임
        int endPage =   Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "boardlist";
    }

    // localhost:8090/board/view?id=1 이런식으로..
    @GetMapping("/board/view")
    public String boardView(Model model, Integer id) {

        // board라는 이름으로 인티저 id값을 넘김
        model.addAttribute("board", boardService.boardView(id));

        return "boardview";
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id, Model model) {

        boardService.boardDelete(id);

        model.addAttribute("message", "글 삭제가 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model) {

        // board라는 이름으로 Integer id값을 넘김
        model.addAttribute("board", boardService.boardView(id));

        return "boardmodify";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board, Model model, MultipartFile file) throws Exception{

        Board boardTemp = boardService.boardView(id);

        // 위 board에서 가져온 title, content를 set한다.
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        // set한 값을 write 메소드로 이동해 저장.  > 업데이트 해주는 느낌
        boardService.write(boardTemp, file);

        model.addAttribute("message", "글 수정이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }

}
