package com.example.qqzone.dao;

import com.example.qqzone.model.Topic;

import java.util.List;

public interface TopicDao {
    List<Topic> queryTopcByUserId(Integer id) throws Exception;

    Topic queryTopcById(Integer id) throws Exception;

    void delTopic(Topic topic) throws Exception;
}
