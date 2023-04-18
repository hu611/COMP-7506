package com.transaction.service;

import com.transaction.mapper.ItemsMapper;
import com.transaction.pojo.DetailedItemDto;
import com.transaction.pojo.Items;
import com.transaction.pojo.ResponseItemsDto;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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


    public byte[] getImageByte(String image_id) throws Exception{
        File file = ResourceUtils.getFile(
                String.format("classpath:images/%s", image_id));
        System.out.println(file.getName());
        InputStream inputStream = new FileInputStream(file);
        return IOUtils.toByteArray(inputStream);
    }

    @Override
    public DetailedItemDto getDetailedItem(String item_id) throws Exception {
        Items items = itemsMapper.selectById(item_id);
        DetailedItemDto detailedItemDto = new DetailedItemDto();
        detailedItemDto.setItem_description(items.getItemDescription());
        detailedItemDto.setItem_name(items.getItemName());
        detailedItemDto.setImg_url(items.getItemPicLoc());
        detailedItemDto.setPrice(items.getPrice());
        return detailedItemDto;
    }
}
