package code.Service;

import code.DTO.UserDTO;
import code.Domain.Board.BoardEntity;
import code.Domain.Board.BoardRepository;
import code.SessionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Service
public class SecurityService
{
    @Autowired
    HttpServletRequest request;

    @Autowired
    BoardRepository boardRepository;

    public Boolean isLogin()
    {
        if(request.getSession().getAttribute(SessionCode.USER_ID.getCode()) == null)
            return false;

        return true;
    }

    public boolean equalsNow(int boardNo)
    {
        BoardEntity e = boardRepository.findByBoardNo(boardNo);

        return request.getSession().getAttribute(SessionCode.USER_NO.getCode()).equals(e.getWriter().getUserNo());
    }

    public UserDTO getUserDTO()
    {
        HttpSession session = request.getSession();

        return new UserDTO().builder()
                .userNo((int) session.getAttribute(SessionCode.USER_NO.getCode()))
                .id((String) session.getAttribute(SessionCode.USER_ID.getCode()))
                .password((String) session.getAttribute(SessionCode.USER_PASSWORD.getCode()))
                .name((String) session.getAttribute(SessionCode.USER_NAME.getCode()))
                .build();
    }

}
