package com.example.qqzone.dao.impl;

import com.example.qqzone.dao.HostReplyDao;
import com.example.qqzone.model.HostReply;
import com.example.qqzone.myssm.basedao.BaseDAO;

import java.util.List;

public class HostReplyDaoImpl extends BaseDAO<HostReply> implements HostReplyDao {
    @Override
    public HostReply queryHostReplyByReplyId(Integer id) throws Exception {
        List<HostReply> hostReplies = this.executeQuery("select id,content,hostReplyDate,author,reply from t_host_reply where reply=? ", id);
        if(hostReplies!=null && hostReplies.size()>0){
            return hostReplies.get(0);
        }
        return null;
    }

    @Override
    public void delHostReply(Integer id) throws Exception {
        this.executeUpdate("delete from t_host_reply where id=?",id);
    }
}
