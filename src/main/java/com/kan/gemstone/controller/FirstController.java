package com.kan.gemstone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

    @GetMapping("/hi")
    public String niceToMeetYou(Model model) {
        model.addAttribute("username", "임재성");
        return "greetings"; // templates/greetins.mustach -> 브라우저 전송
    }

    @GetMapping("/bye")
    public String byeToYou(Model model) {
        model.addAttribute("byename", "재성임");
        return "goodbye";
    }

}
