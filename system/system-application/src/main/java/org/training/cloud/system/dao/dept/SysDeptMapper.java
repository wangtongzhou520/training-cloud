package org.training.cloud.system.dao.dept;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.training.cloud.common.mybatis.extend.LambdaQueryWrapperExtend;
import org.training.cloud.common.mybatis.mapper.BaseMapperExtend;
import org.training.cloud.system.entity.dept.SysDept;

import java.util.List;

/**
 * 部门 mapper
 *
 * @author wangtongzhou
 * @since 2020-06-15 20:22
 */
@Mapper
public interface SysDeptMapper extends BaseMapperExtend<SysDept> {


    /**
     * 查询同一层级下有没有相同的部门
     *
     * @param parentId
     * @param name
     * @return
     */
    default Long countByNameAndParentId(Long parentId, String name) {
        return selectCount(new LambdaQueryWrapperExtend<SysDept>()
                .eq(SysDept::getParentId, parentId)
                .eq(SysDept::getName, name));
    }

    /**
     * 查询子集所有部门
     *
     * @param level
     * @return
     */
    default List<SysDept> selectByChildDeptByLevel(String level) {
        return selectList(new LambdaQueryWrapperExtend<SysDept>()
                .likeIfPresent(SysDept::getLevel, level)
        );
    }

    /**
     * 查询部门树
     *
     * @return
     */
    default List<SysDept> selectAllDept() {
        return selectList();
    }


    default Long countByParentId(Long id) {
        return selectCount(SysDept::getParentId, id);
    }


    /**
     * 批量更新部门level信息
     *
     * @param sysDeptList
     */
    void batchUpdateLevel(@Param("list") List<SysDept> sysDeptList);


}
