package com.example.qqzone.myssm.transcation;

import com.example.qqzone.myssm.util.ConnectionUtl;

import java.sql.Connection;
import java.sql.SQLException;

public class TranscationManager {
    //开启事务
    public static void beginTrans() throws SQLException {
        ConnectionUtl.getConn().setAutoCommit(false);
    }

    //提交事务
    public static void commit() throws SQLException {
        Connection conn = ConnectionUtl.getConn();
        conn.commit();
        ConnectionUtl.close();
    }

    //回滚事务
    public static void rollback() throws SQLException {
        Connection conn = ConnectionUtl.getConn();
        conn.rollback();
        ConnectionUtl.close();
    }
}
