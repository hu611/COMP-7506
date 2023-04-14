package com.transaction.controller;

import com.transaction.config.RestResponse;
import com.transaction.mapper.ItemsMapper;
import com.transaction.pojo.ResponseItemsDto;
import com.transaction.service.TransactionService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
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

    @GetMapping("/getImage")
    @ResponseBody
    public byte[] getImageBasedOnId(@RequestParam("image") String image_id) throws Exception{
        File file = ResourceUtils.getFile(
                String.format("classpath:images/%s", image_id));
        System.out.println(file.getName());
        InputStream inputStream = new FileInputStream(file);
        return IOUtils.toByteArray(inputStream);
    }
}
