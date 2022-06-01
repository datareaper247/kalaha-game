package com.cycleon.game.kalaha.web;

import com.cycleon.game.kalaha.commons.URI;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    @GetMapping("/")
    public String main(Model model) {
        return URI.HOME_TEMPLATE.getUriPath();
    }
}
