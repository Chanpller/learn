package com.example.qqzone.service.impl;

import com.example.qqzone.dao.HostReplyDao;
import com.example.qqzone.dao.ReplyDao;
import com.example.qqzone.dao.TopicDao;
import com.example.qqzone.dao.UserDao;
import com.example.qqzone.model.HostReply;
import com.example.qqzone.model.Reply;
import com.example.qqzone.model.Topic;
import com.example.qqzone.service.ReplyService;

import java.util.List;

public class ReplyServiceImpl implements ReplyService {
    private ReplyDao replyDao;
    private UserDao userDao;
    private TopicDao topicDao;
    private HostReplyDao hostReplyDao;
    @Override
    public List<Reply> queryReplyByTopId(Integer id) throws Exception {
        List<Reply> replies = replyDao.queryReplyByTopId(id);
        for (Reply reply: replies){
            reply.setHostReply(hostReplyDao.queryHostReplyByReplyId(reply.getId()));
            reply.setAuthor(userDao.queryUserById(reply.getAuthor().getId()));
            reply.setTopic(topicDao.queryTopcById(reply.getTopic().getId()));
        }

        return replies;
    }

    @Override
    public void addRply(Integer topicId, String content,Integer auth) throws Exception {
        replyDao.addReply(topicId,content,auth);
    }

    @Override
    public void delReply(Integer replyId) throws Exception {
        //1.根据id获取到reply
        Reply reply = replyDao.getReply(replyId);
        if(reply!=null){
            //2.如果reply有关联的hostReply，则先删除hostReply
            HostReply hostReply = hostReplyDao.queryHostReplyByReplyId(reply.getId());
            if(hostReply!=null){
                hostReplyDao.delHostReply(hostReply.getId());
            }
            //3.删除reply
            replyDao.delReply(replyId);
        }

    }

    @Override
    public void delReplyList(Topic topic) throws Exception {
        List<Reply> replies = replyDao.queryReplyByTopId(topic.getId());
         for(Reply reply: replies){
             //2.如果reply有关联的hostReply，则先删除hostReply
             HostReply hostReply = hostReplyDao.queryHostReplyByReplyId(reply.getId());
             if(hostReply!=null){
                 hostReplyDao.delHostReply(hostReply.getId());
             }
             //3.删除reply
             replyDao.delReply(reply.getId());
         }
    }
}
