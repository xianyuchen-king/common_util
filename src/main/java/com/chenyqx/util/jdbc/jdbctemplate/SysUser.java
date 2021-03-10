package com.chenyqx.util.jdbc.jdbctemplate;

/**
 * @ClassName : SysUser
 * @Description :
 * @Author : chenyqx
 * @Date: 2021-02-07 17:43
 */
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SysUser {
    private Long id;

    private String name;

    private String nickName;

    private String avatar;

    private String password;

    private String salt;

    private String email;

    private String mobile;

    private Byte status;

    private Long deptId;

    private String createBy;

    private Date createTime;

    private String lastUpdateBy;

    private Date lastUpdateTime;

    private Byte delFlag;

    // 省略setter和getter
}