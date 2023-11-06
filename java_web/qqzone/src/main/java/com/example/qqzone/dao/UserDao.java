package com.example.qqzone.dao;

import com.example.qqzone.model.UserBasic;

import java.util.List;

public interface UserDao {
    UserBasic queryUserBasicByLoginIdAndPassword(String loginId,String password) throws Exception;

    List<UserBasic> queryFriendByUserId(Integer id) throws Exception;

    UserBasic queryUserById(Integer id) throws Exception;
}
