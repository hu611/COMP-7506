package com.transaction.service;

import com.transaction.pojo.DetailedItemDto;
import com.transaction.pojo.Items;
import com.transaction.pojo.ResponseItemsDto;

import java.util.List;

public interface TransactionService {
    public ResponseItemsDto getUserImage(String user_id);

    public byte[] getImageByte(String image_id) throws Exception;

    public DetailedItemDto getDetailedItem(String item_id) throws Exception;
}
