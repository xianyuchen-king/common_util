package com.chenyqx.jdbc.example.jdbc_util.jdbc;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.*;
import java.util.List;
import java.util.Properties;

/**
 * @ClassName : JDBCUtils
 * @Description : jdbc工具类封装
 * @Author : chenyqx
 * @Date: 2021-02-07 15:08
 */

public class JDBCUtils {

    private static DataSource dataSource;

    static{
        try{
            Properties properties = new Properties();
            InputStream in = JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");
            properties.load(in);
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static DataSource getDataSource(){
        return dataSource;
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * 关闭连接,归还资源
     * @param rs
     * @param stmt
     * @param conn
     */
    public static void close(ResultSet rs, Statement stmt,Connection conn){
        close(stmt,conn);
        if(rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(Statement stmt,Connection conn){
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(stmt != null){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //关闭连接
    public static  void close(List<Statement> stmts, Connection conn) {
        if(conn != null) { //归还连接
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        for(Statement stmt: stmts) {
            if(stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    public static boolean checkIsEmpty(String sql) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        boolean isEmpty = true;
        Connection conn = getConnection();

        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        if(rs == null){
            isEmpty = true;
        }else if(rs.next()){
            isEmpty = true;
        }else {
            isEmpty = false;
        }

        close(stmt,conn);
        return isEmpty;
    }

    public static boolean execSql(String sql) throws SQLException {
        PreparedStatement preparedStatement = null;
        int flag = -1;
        boolean res = false;
        Connection connection = getConnection();

        preparedStatement = connection.prepareStatement(sql);
        flag = preparedStatement.executeUpdate();
        res = true;

        close(preparedStatement,connection);
        return res;
    }
}
