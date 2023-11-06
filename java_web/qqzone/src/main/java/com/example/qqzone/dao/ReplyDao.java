package com.example.qqzone.dao;

import com.example.qqzone.model.Reply;

import java.util.List;

public interface ReplyDao {
    List<Reply> queryReplyByTopId(Integer id) throws Exception;

    void addReply(Integer topicId, String content, Integer auth) throws Exception;

    void delReply(Integer replyId)throws Exception;

    Reply getReply(Integer replyId) throws Exception;
}
