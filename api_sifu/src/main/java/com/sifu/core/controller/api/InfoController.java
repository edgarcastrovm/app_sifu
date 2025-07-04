package com.sifu.core.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("apiInfoController")
@RequestMapping("/api")
public class InfoController {

    @GetMapping("/hc")
    public ResponseEntity<?> info() {
        return ResponseEntity.ok("Api UP");
    }
}
