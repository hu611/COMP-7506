package com.transaction.controller;

import com.transaction.config.RestResponse;
import com.transaction.mapper.ItemsMapper;
import com.transaction.pojo.DetailedItemDto;
import com.transaction.pojo.ResponseItemsDto;
import com.transaction.pojo.UploadImageRequestDto;
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

    @GetMapping("/getImage")
    @ResponseBody
    public byte[] getImageBasedOnId(@RequestParam("image") String image_name) throws Exception{
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

        System.out.println(file.getOriginalFilename());
        InputStream inputStream = file.getInputStream();
        File targetFile = new File("src/main/resources/"+"a.jpeg");
        targetFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(targetFile);
        byte[]buffer = new byte[1024];
        int len = 0;
        while(((len = inputStream.read(buffer)) != -1)) {
            fos.write(buffer,0,len);
        }
        inputStream.close();
        fos.close();
        return RestResponse.success();
    }
}
