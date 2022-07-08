package code.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import code.DTO.BoardDTO;
import code.DTO.UserDTO;
import code.Domain.User.UserEntity;
import code.Domain.User.UserRepository;
import code.Service.BoardService;
import code.Service.SecurityService;
import code.Service.UserService;
import code.SessionCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class MainController
{

    @Autowired
    final BoardService boardService;
    @Autowired
    final SecurityService securityService;

    @RequestMapping("/") // 최상의 도메인으로 연결.
    public String main()
    {
        return "Home";
    }

    @RequestMapping("/Test")
    public String test()
    {
        if(!securityService.isLogin()) return "Home";

        UserDTO nowUser = securityService.getUserDTO();
        for(int i = 0; i < 300; i++)
        {
            BoardDTO b = new BoardDTO().builder()
                    .boardNo(i)
                    .title(Integer.toString(i))
                    .content(Integer.toString(i*i))
                    .likes(i%20)
                    .views(i%120)
                    .writerName(nowUser.getName())
                    .writerNo(nowUser.getUserNo())
                    .build();

            boardService.Create(b.toEntity());
        }

        return "Home";

    }

}
