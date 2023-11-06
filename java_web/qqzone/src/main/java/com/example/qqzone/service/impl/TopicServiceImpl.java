package com.example.qqzone.service.impl;

import com.example.qqzone.dao.TopicDao;
import com.example.qqzone.dao.UserDao;
import com.example.qqzone.model.Topic;
import com.example.qqzone.model.UserBasic;
import com.example.qqzone.service.ReplyService;
import com.example.qqzone.service.TopicService;

import java.util.List;

public class TopicServiceImpl implements TopicService {
    private TopicDao topicDao;
    private ReplyService replyService;
    private UserDao userDao;
    @Override
    public List<Topic> getTopicList(UserBasic userBasic) throws Exception {
        return topicDao.queryTopcByUserId(userBasic.getId());
    }

    @Override
    public Topic queryTopcDetailById(Integer id) throws Exception {
        Topic topic = topicDao.queryTopcById(id);
        topic.setAuthor(userDao.queryUserById(topic.getAuthor().getId()));
        topic.setReplyList(replyService.queryReplyByTopId(topic.getId()));
        return topic;
    }

    @Override
    public void delTopic(Integer topicId) throws Exception {
        Topic topic = topicDao.queryTopcById(topicId);
        if(topic!=null){
            replyService.delReplyList(topic);
            topicDao.delTopic(topic);
        }
    }
}
