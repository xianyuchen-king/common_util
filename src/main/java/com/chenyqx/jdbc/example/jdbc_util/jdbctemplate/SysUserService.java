package com.chenyqx.jdbc.example.jdbc_util.jdbctemplate;

import java.util.List;

public interface SysUserService {
    /**
     * 保存用户
     * @param user
     */
    public void save(SysUser user);

    /**
     * 删除用户
     * @param id
     */
    public void delete(String id);

    /**
     * 查询全部用户
     * @return
     */
    public List<SysUser> findAll();

}
