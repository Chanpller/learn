package com.example.qqzone.service;

import com.example.qqzone.model.UserBasic;

public interface UserService {
    UserBasic getUserBasicByPaswdAndLoginId(String loginId,String password);
}
