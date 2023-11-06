package com.example.book.dao.impl;

import com.example.book.dao.UserDao;
import com.example.book.model.User;
import myssm.basedao.BaseDAO;

import java.util.List;

public class UserDaoImpl extends BaseDAO<User> implements UserDao {
//    private Integer id;
//    private String uname;
//    private String pwd;
//    private String email;
//    private String role;
    @Override
    public User queryUserByUserNameAndPassword(String username, String password) throws Exception {
        List<User> users = this.executeQuery("select id,uname,pwd,email,role from t_user where uname=? and pwd=? ", username, password);
        if(users!=null && users.size()>0){
            return users.get(0);
        }
        return null;
    }

    @Override
    public User queryUserByUserName(String uname) throws Exception {
        List<User> users = this.executeQuery("select id,uname,pwd,email,role from t_user where uname=?", uname );
        if(users!=null && users.size()>0){
            return users.get(0);
        }
        return null;
    }

    @Override
    public void addUser(String uname, String pwd, String email) throws Exception {
        this.executeUpdate("insert into t_user(uname,pwd,email) values(?,?,?)", uname,pwd, email);
    }
}
