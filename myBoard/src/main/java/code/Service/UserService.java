package code.Service;

import code.DTO.UserDTO;
import code.Domain.User.UserEntity;
import code.Domain.User.UserRepository;
import code.SessionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/*
    서비스
 */

@RequiredArgsConstructor
@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    HttpServletRequest request;

    public UserEntity create(UserDTO user) {

        UserEntity Entity = user.toEntity();

        this.userRepository.save(Entity);

        return Entity;
    }

    public String login(UserDTO user)
    {
        UserEntity entity = userRepository.findById(user.getId());

        if(entity == null) return "그런 유저는 없습니다.";
        if(!entity.getPassword().equals(user.getPassword())) return "비밀번호를 확인하세요.";

        // 로그인 성공 처리
        HttpSession session = request.getSession(true);                         // 세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성하여 반환

        session.setAttribute(SessionCode.USER_NO.getCode(), entity.getUserNo());   // 세션에 로그인 회원 정보 보관
        session.setAttribute(SessionCode.USER_ID.getCode(), entity.getId());   // 세션에 로그인 회원 정보 보관
        session.setAttribute(SessionCode.USER_PASSWORD.getCode(), entity.getPassword());   // 세션에 로그인 회원 정보 보관
        session.setAttribute(SessionCode.USER_NAME.getCode(), entity.getName());   // 세션에 로그인 회원 정보 보관
        return "Success";
    }

    public Boolean logout()
    {
        HttpSession session = request.getSession(true);
        session.setAttribute(SessionCode.USER_NO.getCode(), null);
        session.setAttribute(SessionCode.USER_ID.getCode(), null);
        session.setAttribute(SessionCode.USER_PASSWORD.getCode(), null);
        session.setAttribute(SessionCode.USER_NAME.getCode(), null);

        return true;
    }

}
