package com.example.book.dao;

import com.example.book.model.User;

public interface UserDao {
    User queryUserByUserNameAndPassword(String username, String password) throws Exception;

    User queryUserByUserName(String uname) throws Exception;

    void addUser(String uname, String pwd, String email) throws Exception;

}
