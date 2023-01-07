package com.springcloud.study.system.dao.dept;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springcloud.study.system.entity.dept.SysDeptDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 部门 mapper
 *
 * @author wangtongzhou
 * @since 2020-06-15 20:22
 */
@Repository
public interface SysDeptMapper extends BaseMapper<SysDeptDO> {

    /**
     * 查询同一层级下有没有相同的部门
     *
     * @param parentId 上级id
     * @param name     部门名称
     * @return 行数
     */
    int countByNameAndParentId(@Param("parentId") Integer parentId, @Param("name") String name);

    /**
     * 查询所有的的部门信息
     *
     * @return 部门信息
     */
    List<SysDeptDO> queryAllDept();


    /**
     * 根据level查询下级信息
     *
     * @param level
     * @return 部门信息
     */
    List<SysDeptDO> queryChildDeptByLevel(@Param("level") String level);


    /**
     * 批量更新部门level信息
     *
     * @param sysDeptDoList
     */
    void batchUpdateLevel(List<SysDeptDO> sysDeptDoList);

    /**
     * 根据父级id求总数
     *
     * @param parentId parentId
     * @return count
     */
    int countByParentId(@Param("parentId") Integer parentId);

}
