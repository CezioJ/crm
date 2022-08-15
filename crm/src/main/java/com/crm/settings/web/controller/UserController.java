package com.crm.settings.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/toLogin.do")
    public String toLogin(){
        return "settings/qx/user/login";
    }

//    @RequestMapping("/login")
//    public Boolean login(){
//
//    }
}
