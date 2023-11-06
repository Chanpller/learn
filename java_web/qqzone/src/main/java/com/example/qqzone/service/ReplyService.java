package com.example.qqzone.service;

import com.example.qqzone.model.Reply;
import com.example.qqzone.model.Topic;

import java.util.List;

public interface ReplyService {
    List<Reply> queryReplyByTopId(Integer id) throws Exception;

    void addRply(Integer topicId, String content,Integer auth) throws Exception;

    void delReply(Integer valueOf) throws Exception;

    void delReplyList(Topic topic) throws Exception;;
}
