package com.transaction.controller;

import com.transaction.service.SetInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

//新添加
@Controller
public class setInfoController {

    @Autowired
    SetInfoService setInfoService;

    @PostMapping("/setPassword")
    @ResponseBody
    public boolean setPassword(@RequestParam("userid") String userid,
                               @RequestParam("oldpassword") String oldpassword,
                               @RequestParam("newpassword") String newpassword) {
        return setInfoService.setPassword(userid,oldpassword,newpassword);
    }

    @PostMapping("/setPhone")
    @ResponseBody
    public boolean setPhone(@RequestParam("userid") String userid,
                               @RequestParam("newphone") String newphone) {
        return setInfoService.setPhone(userid,newphone);
    }

    @PostMapping("/setAddress")
    @ResponseBody
    public boolean setAddress(@RequestParam("userid") String userid,
                               @RequestParam("newaddress") String newaddress) {
        return setInfoService.setAddress(userid,newaddress);
    }
}