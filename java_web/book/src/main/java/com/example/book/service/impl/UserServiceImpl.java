package com.example.book.service.impl;

import com.example.book.dao.UserDao;
import com.example.book.model.User;
import com.example.book.service.UserService;

public class UserServiceImpl implements UserService {
    private UserDao userDao;
    @Override
    public User queryUser(String username, String password) throws Exception {
        return userDao.queryUserByUserNameAndPassword(username,password);
    }

    @Override
    public User queryUserByUname(String uname) throws Exception {
        return userDao.queryUserByUserName(uname);
    }

    @Override
    public void addUser(String uname, String pwd, String email) throws Exception {
        userDao.addUser(uname,  pwd,  email);
    }
}
