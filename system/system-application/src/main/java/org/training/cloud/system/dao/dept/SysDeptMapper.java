package org.training.cloud.system.dao.dept;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.training.cloud.common.mybatis.extend.LambdaQueryWrapperExtend;
import org.training.cloud.common.mybatis.mapper.BaseMapperExtend;
import org.training.cloud.system.dto.dept.DeptDTO;
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
                .eq(SysDept::getName, name)
                .eq(SysDept::getDeleteState,false));
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
                .eq(SysDept::getDeleteState,false)
        );
    }

    /**
     * 查询部门树
     *
     * @return
     */
    default List<SysDept> selectDeptList(DeptDTO deptDTO) {
        return selectList(new LambdaQueryWrapperExtend<SysDept>()
                .likeIfPresent(SysDept::getName, deptDTO.getName())
                .eqIfPresent(SysDept::getDeleteState,false)
        );
    }


    /**
     * 根据父节点查询个数
     * 
     * @param id
     * @return
     */
    default Long countByParentId(Long id) {
        return selectCount(new LambdaQueryWrapperExtend<SysDept>()
                .eq(SysDept::getParentId, id)
                .eq(SysDept::getDeleteState,false)
        );
    }




}
