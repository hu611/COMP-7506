package com.transaction.service;

import com.transaction.pojo.Users;

//新添加
public interface SetInfoService {
    boolean setPassword(String user_id, String old_password, String new_password);

    boolean setPhone(String user_id, String new_phone);

    boolean setAddress(String user_id, String new_address);
}