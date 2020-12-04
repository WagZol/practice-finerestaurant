package ITCExcercies.ITCRestaurant.Control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/about")
public class AboutControl {

    @GetMapping()
    public String getAboutPage(){
        return "about";
    }
}