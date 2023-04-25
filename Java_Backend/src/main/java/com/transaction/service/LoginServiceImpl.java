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
        Users loginUser = usersMapper.selectUserByName(username);
        if (loginUser == null) {
            Users user = new Users();
            user.setUserName(username);
            user.setUserPassword(password);
            user.setUserPhone(phoneNumber);
            usersMapper.insert(user);
            return user;
        } else {
            System.out.println("exist user with the same name");
            return null;
        }
    }

    //新添加
    @Override
    public Users login(String username, String password) {
        Users loginUser = usersMapper.selectUserByName(username);
        if (loginUser == null) {
            System.out.println("please input right user name");
            return null;
        } else {
            if (loginUser.getUserPassword().equals(password)) {
                System.out.println("login success");
                return loginUser;
            } else {
                System.out.println("please input right password");
                return null;
            }
        }
    }

    @Override
    public Users loginHku(String username) {
        username = get_hku_username(username);
        Users user = usersMapper.selectUserByName(username);
        if(user != null) {
            return user;
        }
        user = new Users();
        user.setUserPassword(generate_pwd());
        user.setUserName(username);
        usersMapper.insert(user);
        return user;
    }

    public String generate_pwd() {
        return "hkuhkuhkuhku";
    }

    public String get_hku_username(String username) {
        return username + "_hku____";
    }
}
