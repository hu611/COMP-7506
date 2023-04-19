package com.transaction;

import com.transaction.mapper.BoughtItemsMapper;
import com.transaction.mapper.ItemsMapper;
import com.transaction.pojo.BoughtItems;
import com.transaction.pojo.Items;
import com.transaction.pojo.ResponseItemsDto;
import com.transaction.service.LoginService;
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

    @Autowired
    LoginService loginService;

    @Autowired
    BoughtItemsMapper boughtItemsMapper;

    @Autowired
    ItemsMapper itemsMapper;

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

    @Test
    void test_register() {
        loginService.register("aa","ddad","111111");
    }

    @Test
    void test_selectItemsByOffset() {
        Items item = itemsMapper.selectByOffSet(2);
        System.out.println(item);
    }

    @Test
    void test_bought_mapper() {
        List<BoughtItems> boughtItemsList = boughtItemsMapper.getBoughtItemsByBuyerId(12);
    }


}
