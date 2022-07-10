package code.Controller;

import code.Domain.Board.BoardRepository;
import code.Service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Controller
@RequestMapping("/Board")
public class BoardController
{
    @Autowired
    final BoardService boardService;

    @RequestMapping("/Main")
    public String boardmain()
    {
        return "/Board/Main";
    }

    // 여기로 오면 어디서든 게시판 정보를 받아갈 수 있다.
    @GetMapping("/getBoardList") // 서블릿리스폰스를 사용하는건..? ResponseBody 와 차이가 있나?
    public void boardmain(HttpServletResponse response,
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

    @RequestMapping("/Test")
    @ResponseBody
    public String test()
    {

        return boardService.Search(0, 0, "title", "");
    }

}
