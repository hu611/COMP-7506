package com.transaction.service;

import com.transaction.pojo.Items;
import com.transaction.pojo.ResponseItemsDto;

import java.util.List;

public interface TransactionService {
    public ResponseItemsDto getUserImage(String user_id);
}
