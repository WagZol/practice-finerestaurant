package ITCExcercies.ITCRestaurant.Control;

import ITCExcercies.ITCRestaurant.Dto.UserFormDto;
import ITCExcercies.ITCRestaurant.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginControl {

    @Autowired
    UserService userService;

    @ModelAttribute("userToLogin")
    public UserFormDto userLoginFormDto() {
        return new UserFormDto();
    }

    @GetMapping
    public String getLoginPage() {
        return "login";
    }

    @PostMapping
    public String loginUser(@ModelAttribute("userToLogin") @Valid UserFormDto userToLogin,
                            BindingResult result, HttpServletResponse response) {
        if (result.hasErrors()) {
            response.setStatus(401);
            return "login :: #login-form";
        }

        if (!userService.checkCredentials(userToLogin.getUsername(), userToLogin.getPassword())) {
            response.setStatus(401);
            result.rejectValue("username", null, "Wrong email address or password!");
            result.rejectValue("password", null, "Wrong email address or password!!");
            return "login :: #login-form";
        }
        return "login :: #login-form";
    }

}
