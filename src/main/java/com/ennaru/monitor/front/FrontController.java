package com.ennaru.monitor.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/popup")
    public String popup() {
        return "popup";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

}
