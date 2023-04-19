package com.transaction.service;

import com.transaction.mapper.UsersMapper;
import com.transaction.pojo.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    UsersMapper usersMapper;

    @Override
    public Users register(String username, String password, String phoneNumber) {
        Users user = new Users();
        user.setUserName(username);
        user.setUserPassowrd(password);
        user.setUserPhone(phoneNumber);
        usersMapper.insert(user);
        return user;
    }
}
