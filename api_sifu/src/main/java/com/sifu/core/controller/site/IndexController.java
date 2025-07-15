package com.sifu.core.controller.site;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.sifu.core.controller.api.ShopController;

import groovy.util.logging.Log;

@Controller
public class IndexController {
	
	private static final Logger log = LogManager.getLogger(IndexController.class);
	

    @GetMapping
    public String index() {
    	log.info("index() called");
        return "public/index";
    }
    @GetMapping("/site")
    public String site() {
    	log.info("site() called");
        return "public/index";
    }

}
