package com.example.qqzone.dao.impl;

import com.example.qqzone.dao.TopicDao;
import com.example.qqzone.model.Topic;
import com.example.qqzone.myssm.basedao.BaseDAO;

import java.util.List;

public class TopicDaoImpl extends BaseDAO<Topic> implements TopicDao {
    @Override
    public List<Topic> queryTopcByUserId(Integer id) throws Exception {
        return this.executeQuery("select id,title,content,topicDate,author from t_topic where  author=? ", id);
    }

    @Override
    public Topic queryTopcById(Integer id) throws Exception {
        List<Topic> topics = this.executeQuery("select id,title,content,topicDate,author from t_topic where  id=? ", id);
        if(topics!=null && topics.size()>0){
            return topics.get(0);
        }
        return null;
    }

    @Override
    public void delTopic(Topic topic) throws Exception {
        this.executeUpdate("delete from t_topic where id=?",topic.getId());
    }
}
