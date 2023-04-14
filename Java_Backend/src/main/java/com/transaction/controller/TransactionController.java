package com.transaction.controller;

import com.transaction.config.RestResponse;
import com.transaction.mapper.ItemsMapper;
import com.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @GetMapping("/getAllImageLocation")
    @ResponseBody
    public RestResponse getAllImageLocation() {
        String res = transactionService.getAllImageLocations();
        return RestResponse.success(res,"Success");
    }
}
