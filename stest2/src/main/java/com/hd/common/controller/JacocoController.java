package com.hd.common.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JacocoController {
    @RequestMapping("/jacoco")
    public String jacoco() {
        return "index";
    }
}
