package myssm.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtl {
    private static ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();

    public static DataSource dataSource = null;
    static {
        Properties properties = new Properties();

        try {
            properties.load(ConnectionUtl.class.getClassLoader().getResourceAsStream("jdbc.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConn(){
        Connection connection = connectionThreadLocal.get();
        if(connection == null){
            try {
                connection =  dataSource.getConnection();
                connectionThreadLocal.set(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection ;
    }

    public  static void close(){
        try {
            Connection connection = connectionThreadLocal.get();
            if(connection!=null){
                connectionThreadLocal.remove();
                connection.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
