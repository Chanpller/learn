package com.example.qqzone.dao.impl;

import com.example.qqzone.dao.ReplyDao;
import com.example.qqzone.model.HostReply;
import com.example.qqzone.model.Reply;
import com.example.qqzone.myssm.basedao.BaseDAO;

import java.util.Date;
import java.util.List;

public class ReplyDaoImpl extends BaseDAO<Reply> implements ReplyDao {

    @Override
    public List<Reply> queryReplyByTopId(Integer id) throws Exception {
        return this.executeQuery("select id,content,replyDate,author,topic from t_reply where  topic=? ", id);
    }

    @Override
    public void addReply(Integer topicId, String content, Integer auth) throws Exception {
        this.executeUpdate("insert into t_reply(content,replyDate,author,topic) values(?,?,?,?) ", content,new Date(),auth,topicId);
    }

    @Override
    public void delReply(Integer replyId) throws Exception {

        this.executeUpdate("delete from t_reply where id=?",replyId);
    }

    @Override
    public Reply getReply(Integer replyId) throws Exception {
        List<Reply> replies = this.executeQuery("select  id,content,replyDate,author,topic from t_reply where id=? ", replyId);
        if(replies!=null && replies.size()>0){
            return replies.get(0);
        }
        return null;
    }
}
