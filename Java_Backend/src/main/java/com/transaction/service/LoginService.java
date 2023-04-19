package com.transaction.service;


import com.transaction.pojo.Users;

public interface LoginService {
    Users register(String username, String password, String phoneNumber);
}
