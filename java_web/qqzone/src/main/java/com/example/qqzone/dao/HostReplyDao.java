package com.example.qqzone.dao;

import com.example.qqzone.model.HostReply;

public interface HostReplyDao {
    HostReply queryHostReplyByReplyId(Integer id) throws Exception;

    void delHostReply(Integer id)throws Exception;
}
