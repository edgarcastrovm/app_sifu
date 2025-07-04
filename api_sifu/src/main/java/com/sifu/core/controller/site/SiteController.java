package com.sifu.core.controller.site;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/site")
public class SiteController {

    @GetMapping({ "", "/" })
    public String index() {
        return "index";
    }

    @GetMapping("/home")
    public String home() {
        return "index";
    }
}
