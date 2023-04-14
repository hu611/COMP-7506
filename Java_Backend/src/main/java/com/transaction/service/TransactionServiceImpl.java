package com.transaction.service;

import com.transaction.mapper.ItemsMapper;
import com.transaction.pojo.Items;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    public ItemsMapper itemsMapper;

    @Override
    public String getAllImageLocations() {
        List<Items> itemsList = itemsMapper.selectAllItems();
        String ret = "";
        for(Items items: itemsList) {
            ret = ret + items.getItemPicLoc() + " ";
        }
        return ret;
    }
}
