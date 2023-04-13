package com.transaction;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootTest
class SecondHandTransactionApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void test_input_stream_for_images() {
        try {
            File file = ResourceUtils.getFile(
                    "classpath:images/bag.png");
            final ByteArrayResource inputStream = new ByteArrayResource(Files.readAllBytes(file.toPath()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
