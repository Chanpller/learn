package com.example.qqzone.service.impl;

import com.example.qqzone.dao.UserDao;
import com.example.qqzone.model.UserBasic;
import com.example.qqzone.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDao;
    @Override
    public UserBasic getUserBasicByPaswdAndLoginId(String loginId, String password) throws Exception {
        return userDao.queryUserBasicByLoginIdAndPassword(loginId,password);
    }

    @Override
    public List<UserBasic> getFriendByUserId(Integer id) throws Exception {
        List<UserBasic> userBasics = userDao.queryFriendByUserId(id);
        for(UserBasic userBasic : userBasics){
            UserBasic userBasicDatail =  userDao.queryUserById(userBasic.getId());
            userBasic.setId(userBasicDatail.getId());
            userBasic.setLoginId(userBasicDatail.getLoginId());
            userBasic.setHeadImg(userBasicDatail.getHeadImg());
            userBasic.setNickName(userBasicDatail.getNickName());
            userBasic.setPwd(userBasicDatail.getPwd());
        }
        return userBasics;
    }

    @Override
    public UserBasic getUserBasicById(Integer id) throws Exception {
        return userDao.queryUserById(id);
    }
}
