package com.example.qqzone.controller;

import com.example.qqzone.model.UserBasic;
import com.example.qqzone.service.ReplyService;

import javax.servlet.http.HttpServletRequest;

public class ReplyController {
    private ReplyService replyService;
    public String addReply(String topicId,String content, HttpServletRequest request) throws Exception {
        UserBasic userBasic = (UserBasic)request.getSession().getAttribute("userBasic");
        replyService.addRply(Integer.valueOf(topicId),content,userBasic.getId());
        return "redirect:topic.do?operate=topicDetail&id="+topicId;
    }
    public String delReply(String replyId,String topicId) throws Exception {
        replyService.delReply(Integer.valueOf(replyId));
        return "redirect:topic.do?operate=topicDetail&id="+topicId;
    }
}
