package org.training.cloud.system.vo.user;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 用户信息
 *
 * @author wangtongzhou
 * @since 2020-09-01 22:15
 */
@Data
@Accessors(chain = true)
public class SysUserVO {
    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 手机号
     */
    private String telephone;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 加密后的密码
     */
    private String password;

    /**
     * 用户所在部门的id
     */
    private Integer deptName;

    /**
     * 状态，0：正常，1 禁用
     */
    private Integer status;

    /**
     * 用户类型 0 C端 1 管理端
     */
    private Integer userType;

    /**
     * 创建时间
     */
    private Date gmtCreate;


}
