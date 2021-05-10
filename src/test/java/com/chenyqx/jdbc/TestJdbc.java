package com.chenyqx.jdbc;

import com.chenyqx.util.jdbc.jdbc.JDBCUtils;
import com.chenyqx.util.redis.RedisReentrantLockUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.PreparedStatement;

@SpringBootTest
public class TestJdbc {

    @Test
    void TestJdbc1(){
        String sql1 = "select * from iuap_cloud_basedoc.bd_country";
        String sql2 = "update bd_country set code = 'cyq' where name = '科威特'";
        try {
            Connection connection = JDBCUtils.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement(sql2);
//            ResultSet rs = pstmt.executeQuery();
//            while (rs.next()) {
//                System.out.println(rs.getString("name"));
//            }
            try {
                pstmt.executeUpdate();
                int r = 1 / 0;
            }catch (Exception e){
                connection.rollback();
                System.out.println("我回滚了");
                e.printStackTrace();
            }
            connection.commit();
            JDBCUtils.close(pstmt,connection);
        }catch (Exception e){

            e.printStackTrace();
        }
    }


}
