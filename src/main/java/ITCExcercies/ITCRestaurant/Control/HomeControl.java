package ITCExcercies.ITCRestaurant.Control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class HomeControl {

    @GetMapping("/")
    public String redirectToHomePage(){
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String getHomePage(){
        return "home";
    }


}
