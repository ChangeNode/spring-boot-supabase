package com.changenode.frisson.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class Index {

    @GetMapping("/")
    public String overview(HttpServletRequest request) {
        return "index";
    }
}
