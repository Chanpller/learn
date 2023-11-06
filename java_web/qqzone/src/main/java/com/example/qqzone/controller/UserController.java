package com.example.qqzone.controller;

import com.example.qqzone.model.Topic;
import com.example.qqzone.model.UserBasic;
import com.example.qqzone.service.TopicService;
import com.example.qqzone.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class UserController {
    private UserService userService;
    private TopicService topicService;
    public String login(String loginId, String pwd, HttpServletRequest request) throws Exception {
        UserBasic userBasic = userService.getUserBasicByPaswdAndLoginId(loginId, pwd);
        if(userBasic!=null){
            HttpSession session = request.getSession();
            List<UserBasic> friends= userService.getFriendByUserId(userBasic.getId());
            List<Topic> topicList = topicService.getTopicList(userBasic);
            userBasic.setFriendList(friends);
            userBasic.setTopicList(topicList);
            session.setAttribute("userBasic",userBasic);
            session.setAttribute("friend",userBasic);
            return "index";
        }else{
            return "login";
        }

    }
    public String friend(String id, HttpServletRequest request) throws Exception {
        UserBasic userBasic = userService.getUserBasicById(Integer.valueOf(id));
        if(userBasic!=null){
            HttpSession session = request.getSession();
            List<UserBasic> friends= userService.getFriendByUserId(userBasic.getId());
            List<Topic> topicList = topicService.getTopicList(userBasic);
            userBasic.setFriendList(friends);
            userBasic.setTopicList(topicList);
            session.setAttribute("friend",userBasic);
            return "index";
        }else{
            return "login";
        }

    }
}
