package com.example.book.controller;

import com.example.book.model.Cart;
import com.example.book.model.User;
import com.example.book.service.UserService;

import javax.servlet.http.HttpServletRequest;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

public class UserController {
    private UserService userService;
    public String login(String uname, String pwd, HttpServletRequest request) throws Exception {
        User user = userService.queryUser(uname,pwd);
        if(user == null){
            return "user/login";
        }else{
            user.setCart(new Cart());
            request.getSession().setAttribute("currUser",user);
            return "user/login_success";
        }

    }
    public String regist(String uname, String pwd, String email,String verifyCode,HttpServletRequest request) throws Exception {
        String verifyCodeSession = (String)request.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        if(verifyCodeSession.equals(verifyCode)){
            User user = userService.queryUserByUname(uname);
            if(user == null){
                userService.addUser( uname, pwd, email);
                user = userService.queryUserByUname(uname);
                user.setCart(new Cart());
                request.getSession().setAttribute("currUser",user);
                return "user/regist_success";
            }
            return "user/regist";
        }
        return "user/regist";
    }
    public String loginOut(HttpServletRequest request) throws Exception {
        request.getSession().invalidate();
        return "index";
    }

}
