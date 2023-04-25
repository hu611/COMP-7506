package com.transaction.service;


import com.transaction.pojo.Users;

public interface LoginService {
    Users register(String username, String password, String phoneNumber);

    //新添加
    Users login(String username, String password);

    Users loginHku(String username);
}
