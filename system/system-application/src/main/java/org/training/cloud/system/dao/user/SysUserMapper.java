package org.training.cloud.system.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.training.cloud.common.core.vo.PageParam;
import org.training.cloud.common.mybatis.mapper.BaseMapperExtend;
import org.training.cloud.system.entity.user.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * user mapper
 *
 * @author wangtongzhou
 * @since 2020-08-31 21:33
 */
@Mapper
public interface SysUserMapper extends BaseMapperExtend<SysUser> {


    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    default SysUser queryByUserName(String username) {
        return selectOne(SysUser::getUsername, username);
    }

//    /**
//     * 查询mail有没有注册过
//     *
//     * @param mail mail
//     * @param id   用户id
//     * @return 数量
//     */
//    int countByMail(@Param("mail") String mail, @Param("id") Integer id);
//
//    /**
//     * 查询mail有没有注册过
//     *
//     * @param telephone telephone
//     * @param id        id
//     * @return 数量
//     */
//    int countByTelephone(@Param("telephone") String telephone,
//                         @Param("id") Integer id);
//
//    /**
//     * 根据部门名称分页查询用户信息
//     *
//     * @param deptId    deptId
//     * @param pageParam pageParam
//     * @return 用户信息
//     */
//    List<SysUser> queryPageByDeptId(@Param("deptId") String deptId,
//                                    @Param("pageParam") PageParam pageParam);
//
//    /**
//     * 根据部门查询总条数
//     *
//     * @param deptId deptId
//     * @return 用户信息
//     */
//    Long countByDeptId(@Param("deptId") String deptId);
}
