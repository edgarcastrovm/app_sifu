package com.sifu.core.controller.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("apiInfoController")
@RequestMapping("/api")
public class InfoController {
	
	 private static final Logger log = LogManager.getLogger(InfoController.class);

    @GetMapping("/hc")
    public ResponseEntity<?> info() {
    	log.info("info() called");
        return ResponseEntity.ok("Api UP");
    }
}
