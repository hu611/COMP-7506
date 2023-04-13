package com.transaction.controller;

import com.transaction.config.RestResponse;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

@Controller
public class testcontroller {
    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        System.out.println("received request");
        return "Congrats! This is a test";
    }

    @RequestMapping("/testimage")
    @ResponseBody
    public RestResponse test_image() {
        try {
            File file = ResourceUtils.getFile(
                    "classpath:images/bag.png");
            final ByteArrayResource inputStream = new ByteArrayResource(Files.readAllBytes(file.toPath()));
            return RestResponse.success(inputStream);
        } catch (Exception e) {
            return RestResponse.fail("failed to send");
        }

    }

}
