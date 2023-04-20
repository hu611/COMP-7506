package com.transaction.service;

import com.transaction.pojo.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TransactionService {
    public ResponseItemsDto getUserImage(String user_id);

    public byte[] getImageByte(String image_id) throws Exception;

    public DetailedItemDto getDetailedItem(String item_id) throws Exception;

    public void saveImage(MultipartFile file) throws Exception;

    public void sellItem(SellItemsDto sellItemsDto) throws Exception;

    public void buyItem(BuyItemsDto buyItemsDto) throws Exception;

    ResponseItemsDto getUserBoughtItem(String user_id);

    ResponseItemsDto getUserSoldItem(String user_id);

    Users getUserByUserId(String user_id);

    Users addBalanceByUserId(String user_id);
}
