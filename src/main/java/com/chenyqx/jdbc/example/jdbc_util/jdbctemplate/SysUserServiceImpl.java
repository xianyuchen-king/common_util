package com.chenyqx.jdbc.example.jdbc_util.jdbctemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName : SysUserServiceImpl
 * @Description :
 * @Author : chenyqx
 * @Date: 2021-02-07 17:50
 */
@Service
public class SysUserServiceImpl implements SysUserService{

    @Autowired
    private SysUserDao sysUserDao;

    @Override
    public void save(SysUser user) {
        sysUserDao.save(user);
    }

    @Override
    public void delete(String id) {
        sysUserDao.delete(id);
    }

    @Override
    public List<SysUser> findAll() {
        return sysUserDao.findAll();
    }
}
