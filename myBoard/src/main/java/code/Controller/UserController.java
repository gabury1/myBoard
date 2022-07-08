package code.Controller;

import code.DTO.UserDTO;
import code.Domain.User.UserEntity;
import code.Domain.User.UserRepository;
import code.Service.UserService;
import code.SessionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static javax.swing.JOptionPane.showMessageDialog;

@RequiredArgsConstructor
@Controller
@RequestMapping("/User")
public class UserController {

    @Autowired
    private final UserService userService;
    @Autowired
    private final UserRepository userRepository;

    @RequestMapping("/Signup")
    public String signup()
    {
        return "/User/Signup";
    }

    @PostMapping("/Signup")
    @ResponseBody
    public String Signup(@Valid UserDTO user, BindingResult errors)
    {
        //BindResult로 Ajax 통신하는 방법을 몰라서... 그냥 이렇게 해봤읍니다.
        //디테일 수정때 수정 예정
        if (errors.hasErrors()) {
            if(user.getId().length() < 6 || 25 < user.getId().length())
                return "ID를 확인하세요.";
            if(user.getPassword().length() < 6 || 25 < user.getPassword().length())
                return "비밀번호를 확인하세요.";
            if(user.getName().length() < 2 || 25 < user.getName().length())
                return "이름을 확인하세요";
        }

        //
        try {
            userService.create(user);

        }catch(DataIntegrityViolationException e) {
            e.printStackTrace();
            return "이미 등록된 사용자입니다.";
        }catch(Exception e) {
            e.printStackTrace();
            return e.toString();
        }

        return "Success";
    }

    @RequestMapping("/Login")
    public String Login()
    {

        return "/User/Login";
    }

    @PostMapping("/Login")
    @ResponseBody
    public String Login(UserDTO user)
    {
        return userService.login(user);
    }

    @RequestMapping("/Logout")
    public String Logout()
    {
        userService.logout();
        return "Home";
    }

}