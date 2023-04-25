package com.transaction.controller;

import com.transaction.config.RestResponse;

import com.transaction.pojo.Users;
import com.transaction.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/register")
    @ResponseBody
    public Users register(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          @RequestParam("phone") String phone) {
        return loginService.register(username,password,phone);
    }

    //新添加
    @PostMapping("/login")
    @ResponseBody
    public Users login(@RequestParam("username") String username,
                       @RequestParam("password") String password) {
        return  loginService.login(username,password);
    }

    @PostMapping("/loginhku")
    @ResponseBody
    public Users loginHku(@RequestParam("username") String username) {
        return loginService.loginHku(username);
    }

}
