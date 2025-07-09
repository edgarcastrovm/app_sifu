package com.sifu.core.controller.site;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/")
    public String index() {
        return "/admin/panel";
    }

    @GetMapping("/panel")
    public String panel() {
        return "/admin/panel";
    }

    @GetMapping("/reportes-ia")
    public String reportesIa() {
        return "/admin/reportes/reportes_ia";
    }
}
