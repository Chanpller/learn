package com.example.book.service;

import com.example.book.model.User;

public interface UserService {
    User queryUser(String username, String password) throws Exception;

    User queryUserByUname(String uname) throws Exception;

    void addUser(String uname, String pwd, String email) throws Exception;
}
