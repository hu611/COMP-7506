package com.transaction.service;

import com.transaction.mapper.UsersMapper;
import com.transaction.pojo.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//新添加
@Service
public class SetInfoServiceImpl implements SetInfoService {
    @Autowired
    UsersMapper usersMapper;

    @Override
    public boolean setPassword(String user_id, String old_password, String new_password) {
        Users currentUser = usersMapper.selectUserById(user_id);
        if (currentUser.getUserPassword().equals(old_password)) {
            usersMapper.updateUserPassword(user_id, new_password);
            System.out.println("set password: success");
            return true;
        } else {
            System.out.println("input wrong password");
            return false;
        }
    }

    @Override
    public boolean setPhone(String user_id, String new_phone) {
        usersMapper.updateUserPhone(user_id, new_phone);
        System.out.println("set phone: success");
        return true;
    }

    @Override
    public boolean setAddress(String user_id, String new_address) {
        usersMapper.updateUserAddress(user_id, new_address);
        System.out.println("set address: success");
        return true;
    }
}