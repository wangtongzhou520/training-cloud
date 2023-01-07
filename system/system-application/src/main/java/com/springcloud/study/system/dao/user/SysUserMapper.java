package com.springcloud.study.system.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springcloud.study.common.core.vo.PageParam;
import com.springcloud.study.system.entity.user.SysUserDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * user mapper
 *
 * @author wangtongzhou
 * @since 2020-08-31 21:33
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUserDO> {

    /**
     * 用户信息
     *
     * @param userName userName
     * @return 用户信息
     */
    SysUserDO querySysByUserName(@Param("userName") String userName);

    /**
     * 查询mail有没有注册过
     *
     * @param mail mail
     * @param id   用户id
     * @return 数量
     */
    int countByMail(@Param("mail") String mail, @Param("id") Integer id);

    /**
     * 查询mail有没有注册过
     *
     * @param telephone telephone
     * @param id        id
     * @return 数量
     */
    int countByTelephone(@Param("telephone") String telephone,
                         @Param("id") Integer id);

    /**
     * 根据部门名称分页查询用户信息
     *
     * @param deptId    deptId
     * @param pageParam pageParam
     * @return 用户信息
     */
    List<SysUserDO> queryPageByDeptId(@Param("deptId") String deptId,
                                      @Param("pageParam") PageParam pageParam);

    /**
     * 根据部门查询总条数
     *
     * @param deptId deptId
     * @return 用户信息
     */
    int countByDeptId(@Param("deptId") String deptId);
}
