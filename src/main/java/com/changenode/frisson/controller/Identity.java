package com.changenode.frisson.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/public")
public class Identity {

    @GetMapping("/sign-in")
    public String signIn(HttpServletResponse response, Model model,
                         @RequestParam(value = "access_token", required = false) String accessToken,
                         @RequestParam(value = "login", required = false) String login,
                         @RequestParam(value = "error_description", required = false) String error) {
        if (error != null)
            model.addAttribute("error", error);

        if (accessToken != null || login != null)
            model.addAttribute("hasToken", true);
        else
            model.addAttribute("hasToken", false);
        if (accessToken != null) {
            Cookie accessTokenCookie = new Cookie("access-token", null);
            accessTokenCookie.setMaxAge(-1);
            accessTokenCookie.setPath("/");
            response.addCookie(accessTokenCookie);
        }

        return "identity/sign-in";
    }

    @GetMapping("/update-password")
    public String updatePassword(
            @RequestParam(name = "error_description", required = false) String errorDescription,
            Model model) {

        if (errorDescription != null)
            model.addAttribute("error", errorDescription);

        return "identity/update-password";
    }

    @GetMapping("/create-account")
    public String createAccount() {
        return "identity/create-account";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "identity/forgot-password";
    }

    @GetMapping("/sign-out")
    public String logout(HttpServletResponse response) {
        Cookie accessToken = new Cookie("access-token", null);
        accessToken.setMaxAge(-1);
        accessToken.setPath("/");
        response.addCookie(accessToken);
        return "identity/sign-out";
    }
}
