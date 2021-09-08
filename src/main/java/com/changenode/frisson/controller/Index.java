package com.changenode.frisson.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class Index {

    @GetMapping("/")
    public String overview(HttpServletRequest request) {
        return "index";
    }
}
