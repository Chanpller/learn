package com.example.qqzone.dao.impl;

import com.example.qqzone.dao.UserDao;
import com.example.qqzone.model.UserBasic;
import com.example.qqzone.myssm.basedao.BaseDAO;

import java.util.List;

public class UserDaoImpl extends BaseDAO<UserBasic> implements UserDao {
    @Override
    public UserBasic queryUserBasicByLoginIdAndPassword(String loginId, String password) throws Exception {
        List<UserBasic> userBasics = this.executeQuery("select id,loginId,nickName,pwd,headImg from t_user_basic where loginId=? and pwd=? ", loginId, password);
        if(userBasics!=null && userBasics.size()>0){
            return userBasics.get(0);
        }
        return null;
    }

    @Override
    public List<UserBasic> queryFriendByUserId(Integer id) throws Exception {
        return this.executeQuery("select fid as id from t_friend where uid=?",id);
    }

    @Override
    public UserBasic queryUserById(Integer id) throws Exception {
        List<UserBasic> userBasics = this.executeQuery("select id,loginId,nickName,pwd,headImg from t_user_basic where id=? ",id);
        if(userBasics!=null && userBasics.size()>0){
            return userBasics.get(0);
        }
        return null;
    }
}
