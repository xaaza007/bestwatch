package pl.sda.bestwatch;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BestwatchController {
    @GetMapping("/api/bestwatch")
    public Anything get() {
        return new Anything();
    }

}
