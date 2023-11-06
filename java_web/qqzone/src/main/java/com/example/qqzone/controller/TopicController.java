package com.example.qqzone.controller;

import com.example.qqzone.model.Topic;
import com.example.qqzone.model.UserBasic;
import com.example.qqzone.service.TopicService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class TopicController {
    private TopicService topicService;
    public String topicDetail(String id, HttpServletRequest request) throws Exception {
        Topic topic = topicService.queryTopcDetailById(Integer.valueOf(id));
        HttpSession session = request.getSession();
        session.setAttribute("topic",topic);
        return "frames/detail";
    }
    public String delTopic(Integer topicId) throws Exception {
        topicService.delTopic(topicId);
        return "redirect:topic.do?operate=getTopicList" ;
    }

    public String getTopicList(HttpServletRequest request) throws Exception {
        //从session中获取当前用户信息
        UserBasic userBasic = (UserBasic)request.getSession().getAttribute("userBasic");
        //再次查询当前用户关联的所有的日志
        List<Topic> topicList = topicService.getTopicList(userBasic);
        //设置一下关联的日志列表(因为之前session中关联的friend的topicList和此刻数据库中不一致）
        userBasic.setTopicList(topicList);
        //重新覆盖一下friend中的信息(为什么不覆盖userbasic中？因为main.html页面迭代的是friend这个key中的数据)
        request.getSession().setAttribute("friend",userBasic);
        return "frames/main";
    }
}
