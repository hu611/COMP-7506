package com.transaction.service;

import com.transaction.mapper.BoughtItemsMapper;
import com.transaction.mapper.ItemsMapper;
import com.transaction.mapper.UsersMapper;
import com.transaction.pojo.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    public ItemsMapper itemsMapper;

    @Autowired
    public UsersMapper usersMapper;

    @Autowired
    public BoughtItemsMapper boughtItemsMapper;

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
        Items items = itemsMapper.selectByOffSet(Integer.parseInt(item_id));
        DetailedItemDto detailedItemDto = new DetailedItemDto();
        detailedItemDto.setItem_description(items.getItemDescription());
        detailedItemDto.setItem_name(items.getItemName());
        detailedItemDto.setImg_url(items.getItemPicLoc());
        detailedItemDto.setPrice(items.getPrice());
        return detailedItemDto;
    }

    @Override
    public void saveImage(MultipartFile file) throws Exception { ;
        InputStream inputStream = file.getInputStream();
        File targetFile = new File("src/main/resources/images/"+file.getOriginalFilename() + ".png");
        FileOutputStream fos = new FileOutputStream(targetFile);
        byte[]buffer = new byte[1024];
        int len = 0;
        while(((len = inputStream.read(buffer)) != -1)) {
            fos.write(buffer,0,len);
        }
        inputStream.close();
        fos.close();
    }

    @Override
    public void sellItem(SellItemsDto sellItemsDto) throws Exception {
        Items items = new Items();
        items.setItemName(sellItemsDto.getItem_name());
        items.setItemPicLoc(sellItemsDto.getItem_name() + ".png");
        items.setPrice(Integer.parseInt(sellItemsDto.getPrice()));
        items.setItemDescription(sellItemsDto.getItem_description());
        items.setUserId(sellItemsDto.getUserId());
        itemsMapper.insert(items);
    }

    @Transactional
    @Override
    public void buyItem(BuyItemsDto buyItemsDto) throws Exception {
        Items item = itemsMapper.selectByOffSet(Integer.parseInt(buyItemsDto.getItemId()));
        Users user = usersMapper.selectById(buyItemsDto.getUserId());
        if(!canAfford(user, item)) {
            throw new RuntimeException("User does not have enough money");
        }
        user.setUserBalance(user.getUserBalance() - item.getPrice());
        BoughtItems boughtItems = new BoughtItems();
        boughtItems.setItemName(item.getItemName());
        boughtItems.setItemPicLoc(item.getItemPicLoc());
        boughtItems.setBuyerId(user.getUserId());
        boughtItems.setSellerId(Integer.parseInt(item.getUserId()));
        boughtItems.setDealPrice(item.getPrice());
        itemsMapper.deleteById(item.getItemId());
        usersMapper.updateById(user);
        boughtItemsMapper.insert(boughtItems);
    }

    public boolean canAfford(Users user, Items item) {
        return user.getUserBalance() >= item.getPrice();
    }
}
