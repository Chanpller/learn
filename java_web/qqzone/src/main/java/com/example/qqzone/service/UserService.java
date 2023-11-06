package com.example.qqzone.service;

import com.example.qqzone.model.UserBasic;

import java.util.List;

public interface UserService {
    UserBasic getUserBasicByPaswdAndLoginId(String loginId,String password) throws Exception;

    List<UserBasic> getFriendByUserId(Integer id) throws Exception;

    UserBasic getUserBasicById(Integer id) throws Exception;
}
