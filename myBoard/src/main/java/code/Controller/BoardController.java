package code.Controller;

import code.DTO.BoardDTO;
import code.Domain.Board.BoardRepository;
import code.Service.BoardService;
import code.Service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@RequiredArgsConstructor
@Controller
@RequestMapping("/Board")
public class BoardController
{
    @Autowired
    final BoardService boardService;
    @Autowired
    final SecurityService securityService;

    @RequestMapping("/Main")
    public String boardmain()
    {
        return "/Board/Main";
    }

    @GetMapping("/GetBoardList") // 서블릿리스폰스를 사용하는건..? @ResponseBody 와 차이가 있나?
                                 // 여기로 오면 어디서든 게시판 리스트를 받아갈 수 있다.
    public void BoardListAPI(HttpServletResponse response,
                            @RequestParam("key") String key ,
                            @RequestParam("keyword") String keyword ,
                            @RequestParam("page") int page ,
                            @RequestParam("mode") int mode
                            )
    {

        try
        {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().println(boardService.Search(page, mode, key, keyword));
        }
        catch(Exception e){e.printStackTrace();}

        return;
    }


    @RequestMapping("/{boardNo}") // 글 상세 정보를 매핑해주는 매서드
    public String info(@PathVariable("boardNo") int boardNo)
    {

        return "/Board/Info";
    }


    @GetMapping("/Info") // 게시물 번호만 넘겨주면 게시물 정보를 반환해준다.
    public void BoardInfoAPI(HttpServletResponse response, @Param("boardNo") int boardNo)
    {
        try
        {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json"); // JSON
            response.getWriter().println(boardService.BoardInfo(boardNo));
        }
        catch(Exception e){e.printStackTrace();}

    }


    @GetMapping("/Like") // 글에서 '추천' 버튼을 누르면 AJAX로 추천인 번호를 보내준다.
    public void BoardInfoAPI(HttpServletResponse response, @Param("writerNo") int writerNo, @Param("boardNo") int boardNo)
    {
        try
        {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json"); // JSON
            response.getWriter().println(boardService.BoardLike(writerNo, boardNo));
        }
        catch(Exception e){e.printStackTrace();}

    }

    @RequestMapping("/Write") // 글 쓰기 화면
    public String write()
    {
        if(!securityService.isLogin()) return "/Board/Main";
        return "/Board/Write";
    }


    @RequestMapping("/{boardNo}/Modify") // 수정 매핑
    public String modify()
    {
        if(!securityService.isLogin()) return "/Board/Main";

        return "/Board/Modify";
    }


    @GetMapping("/Create") // 글 쓰고 데이터를 여기로 보냄.
    @ResponseBody
    public String Create(@Valid BoardDTO boardDTO, BindingResult errors)
    {
        if (errors.hasErrors()) {
            if (boardDTO.getTitle().length() <= 0 || 50 < boardDTO.getTitle().length()) return "제목을 확인해주세요.";
            if (boardDTO.getContent().length() <= 0) return "내용을 확인해주세요.";
            //return response.invalidFields(common.refineErrors(errors));
        }

        boardService.Create(boardDTO.toEntity());


        return "success";
    }
    @GetMapping("/Update")
    @ResponseBody()
    public String Update(@Valid BoardDTO boardDTO, BindingResult errors)
    {
        if (errors.hasErrors()) {
            if (boardDTO.getTitle().length() <= 0 || 50 < boardDTO.getTitle().length()) return "제목을 확인해주세요.";
            if (boardDTO.getContent().length() <= 0) return "내용을 확인해주세요.";
            //return response.invalidFields(common.refineErrors(errors));
        }

        // 바뀐 내용을 전달받아 수정해주고 문자열을 반환한다.
        return boardService.Update(boardDTO.getBoardNo(), boardDTO.getTitle(), boardDTO.getContent());
    }
}
