package com.transaction;

import com.transaction.pojo.Items;
import com.transaction.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@SpringBootTest
class SecondHandTransactionApplicationTests {
    @Autowired
    TransactionService transactionService;
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

    @Test
    void test_get_all_transactions() {

    }


}
