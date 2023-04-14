package com.transaction.service;

import com.transaction.mapper.ItemsMapper;
import com.transaction.pojo.Items;
import com.transaction.pojo.ResponseItemsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    public ItemsMapper itemsMapper;

    @Override
    public ResponseItemsDto getUserImage(String user_id) {
        Map<String, Object> map = new HashMap<>();
        if(user_id != null) {
            //user id is an optional value, might be null
            map.put("user_id",user_id);
        }
        List<Items> itemsList = itemsMapper.selectAllItems(map);
        String[] imageUrlList = new String[itemsList.size()];
        String[] itemNameList = new String[itemsList.size()];
        int idx = 0;
        for(Items items: itemsList) {
            imageUrlList[idx] = items.getItemPicLoc();
            itemNameList[idx] = items.getItemName();
            idx++;
        }
        ResponseItemsDto responseItemsDto = new ResponseItemsDto();
        responseItemsDto.setImageUrlList(imageUrlList);
        responseItemsDto.setItemNameList(itemNameList);
        System.out.println(responseItemsDto);
        return responseItemsDto;
    }
}
