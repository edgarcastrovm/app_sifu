package com.sifu.core.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class InfoController {

    @GetMapping
    public ResponseEntity<?> hi() {
        return ResponseEntity.ok("Welcome to SiFu Api");
    }
    @GetMapping("/hc")
    public ResponseEntity<?> hc() {
        return ResponseEntity.ok("ok");
    }
}
