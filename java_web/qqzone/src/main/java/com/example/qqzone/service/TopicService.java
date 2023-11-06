package com.example.qqzone.service;

import com.example.qqzone.model.Topic;
import com.example.qqzone.model.UserBasic;

import java.util.List;

public interface TopicService {
    List<Topic> getTopicList(UserBasic userBasic) throws Exception;

    Topic queryTopcDetailById(Integer valueOf) throws Exception;

    void delTopic(Integer topicId) throws Exception;
}
