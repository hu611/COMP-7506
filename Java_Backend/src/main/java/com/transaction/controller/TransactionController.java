package com.transaction.controller;

import com.transaction.config.RestResponse;
import com.transaction.mapper.ItemsMapper;
import com.transaction.pojo.*;
import com.transaction.service.TransactionService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Controller
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @GetMapping("/getUserImage")
    @ResponseBody
    public RestResponse getUserImage(@RequestParam(value = "user_id",required = false) String user_id) {
        ResponseItemsDto res = transactionService.getUserImage(user_id);

        return RestResponse.success(res,"Success");
    }

    @GetMapping("/getUserBoughtItem")
    @ResponseBody
    public RestResponse getUserBoughtItem(@RequestParam(value = "user_id") String user_id) {
        ResponseItemsDto res = transactionService.getUserBoughtItem(user_id);
        return RestResponse.success(res,"Success");
    }

    @GetMapping("/getUserSoldItem")
    @ResponseBody
    public RestResponse getUserSoldItem(@RequestParam(value = "user_id") String user_id) {
        ResponseItemsDto res = transactionService.getUserSoldItem(user_id);
        return RestResponse.success(res,"Success");
    }

    @GetMapping("/getImage")
    @ResponseBody
    public byte[] getImageBasedOnId(@RequestParam("image") String image_name) throws Exception{
        System.out.println(image_name);
        return transactionService.getImageByte(image_name);
    }

    @GetMapping("/getDetailedItem")
    @ResponseBody
    public DetailedItemDto getDetailedItem(@RequestParam("item_id") String item_id) throws Exception {
        return transactionService.getDetailedItem(item_id);
    }

    @RequestMapping("/uploadImage")
    @ResponseBody
    public RestResponse uploadImage(@RequestParam("file")MultipartFile file) throws Exception{
        transactionService.saveImage(file);
        return RestResponse.success();
    }

    @PostMapping("/sellItem")
    @ResponseBody
    public RestResponse sellItem(@RequestBody SellItemsDto sellItemsDto) throws Exception {
        transactionService.sellItem(sellItemsDto);
        return RestResponse.success();
    }

    @PostMapping("/buyItem")
    @ResponseBody
    public RestResponse buyItem(@RequestBody BuyItemsDto buyItemsDto) throws Exception {
        transactionService.buyItem(buyItemsDto);
        return RestResponse.success();
    }

    @GetMapping("/getBalance")
    @ResponseBody
    public Users getBalance(@RequestParam("user_id") String user_id) {
        return transactionService.getUserByUserId(user_id);
    }

    @PostMapping("/addBalance")
    @ResponseBody
    public Users addBalance(@RequestParam("user_id") String user_id) {
        return transactionService.addBalanceByUserId(user_id);
    }

}
