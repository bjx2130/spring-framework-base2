package com.example.oauth.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/sinoLogin")
    public String hello(HttpSession session,String redirect_uri) {
        //这边我们,默认是返到templates下的login.html
    	session.setAttribute("redirect_uri", redirect_uri);
        return "login";
    }
}