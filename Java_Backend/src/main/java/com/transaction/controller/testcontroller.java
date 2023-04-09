package com.transaction.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class testcontroller {
    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        System.out.println("received request");
        return "Congrats! This is a test";
    }
}
