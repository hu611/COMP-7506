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

    String file_loc_prefix = "src/main/resources/images/";

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
        File file = new File(file_loc_prefix + image_id);
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
        File targetFile = new File(file_loc_prefix +file.getOriginalFilename() + ".png");
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
        Users buyer = usersMapper.selectById(buyItemsDto.getUserId());
        Users seller = usersMapper.selectById(item.getUserId());
        if(!canAfford(buyer, item)) {
            throw new RuntimeException("User does not have enough money");
        }
        //update buyer and seller balance
        buyer.setUserBalance(buyer.getUserBalance() - item.getPrice());
        seller.setUserBalance(seller.getUserBalance() + item.getPrice());

        //configure boughtitems
        BoughtItems boughtItems = new BoughtItems();
        boughtItems.setItemName(item.getItemName());
        boughtItems.setItemPicLoc(item.getItemPicLoc());
        boughtItems.setBuyerId(buyer.getUserId());
        boughtItems.setSellerId(Integer.parseInt(item.getUserId()));
        boughtItems.setDealPrice(item.getPrice());
        itemsMapper.deleteById(item.getItemId());
        usersMapper.updateById(buyer);
        usersMapper.updateById(seller);
        boughtItemsMapper.insert(boughtItems);
    }

    @Override
    public Users getUserByUserId(String user_id) {
        return usersMapper.selectById(user_id);
    }

    public boolean canAfford(Users user, Items item) {
        return user.getUserBalance() >= item.getPrice();
    }

    public ResponseItemsDto getUserBoughtItem(String user_id) {

        List<BoughtItems> boughtItemsList = boughtItemsMapper.getBoughtItemsByBuyerId(Integer.parseInt(user_id));
        return convertBoughtItemsToResponseItemsDto(boughtItemsList);
    }

    public ResponseItemsDto getUserSoldItem(String user_id) {
        List<BoughtItems> soldItemsList = boughtItemsMapper.getBoughtItemsBySellerId(Integer.parseInt(user_id));
        return convertBoughtItemsToResponseItemsDto(soldItemsList);
    }

    public ResponseItemsDto convertBoughtItemsToResponseItemsDto(List<BoughtItems> boughtItemsList) {
        ResponseItemsDto responseItemsDto = new ResponseItemsDto();
        String[] imageUrlList = new String[boughtItemsList.size()];
        String[] itemNameList = new String[boughtItemsList.size()];
        int idx = 0;
        for(BoughtItems boughtItems: boughtItemsList) {
            imageUrlList[idx] = boughtItems.getItemPicLoc();
            itemNameList[idx++] = boughtItems.getItemName();
        }
        responseItemsDto.setItemNameList(itemNameList);
        responseItemsDto.setImageUrlList(imageUrlList);
        return responseItemsDto;
    }

    @Override
    public Users addBalanceByUserId(String user_id) {
        Users user = usersMapper.selectById(user_id);
        if(user.getUserBalance() > 100000) {
            return user;
        }
        user.setUserBalance(user.getUserBalance() + 1000);
        usersMapper.updateById(user);
        return user;
    }

}
