package com.chenyqx.jdbc.example.jdbc_util.jdbctemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

/**
 * @ClassName : SysUserDao
 * @Description :
 * @Author : chenyqx
 * @Date: 2021-02-07 17:53
 */
@Repository
public class SysUserDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    TransactionTemplate transactionTemplate;

    public void save(SysUser sysUser){
        String sql = "insert into sys_user(id, name, nick_name, password, email) values(?,?,?,?,?)";
        jdbcTemplate.update(sql,sysUser.getId(),sysUser.getName(),sysUser.getNickName(),sysUser.getPassword(),sysUser.getEmail());
    }

    public void delete(String id){
        String sql = "delete from sys_user where id = ?;";
//        jdbcTemplate.update(sql,id);
        transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus transactionStatus) {
                Object savepoint = transactionStatus.createSavepoint();
                // DML执行
                try {
                    int rs2 = jdbcTemplate.update(sql,id);
//                    int e = 1/0;
                } catch (Throwable e) {
                    transactionStatus.setRollbackOnly();
                    System.out.println("我回滚了");
                    // transactionStatus.rollbackToSavepoint(savepoint);
                }
                return null;
            }
        });
    }

    public List<SysUser> findAll(){
        String sql = "select * from sys_user";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper(SysUser.class));
    }

}
