package com.example.qqzone.myssm.util;

import java.sql.*;

public class ConnectionUtl {
    private static ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();
    public final static String DRIVER = "com.mysql.jdbc.Driver" ;
    public final static String URL = "jdbc:mysql://localhost:3306/fruitdb?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    public final static String USER = "root";
    public final static String PWD = "HHXXttxs19" ;

    public static Connection getConn(){
        Connection connection = connectionThreadLocal.get();
        if(connection == null){
            try {

                //1.加载驱动
                Class.forName(DRIVER);
                //2.通过驱动管理器获取连接对象
                connection =  DriverManager.getConnection(URL, USER, PWD);
                connectionThreadLocal.set(connection);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection ;
    }

    public  static void close(){
        try {
            Connection connection = connectionThreadLocal.get();
            if(connection!=null){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
