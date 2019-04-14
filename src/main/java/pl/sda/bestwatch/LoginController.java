package pl.sda.bestwatch;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/app/login")
    public String showLoginView() {
        return "login";
    }
}
