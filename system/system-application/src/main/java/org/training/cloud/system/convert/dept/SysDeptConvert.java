package org.training.cloud.system.convert.dept;

import org.training.cloud.system.dto.dept.SaveDeptDTO;
import org.training.cloud.system.dto.dept.UpdateDeptDTO;
import org.training.cloud.system.entity.dept.SysDept;
import org.training.cloud.system.vo.dept.DeptTreeVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * sysDeptDTO convert sysDeptDO
 *
 * @author wangtongzhou
 * @since 2020-06-15 21:13
 */
@Mapper
public interface SysDeptConvert {

    SysDeptConvert INSTANCE = Mappers.getMapper(SysDeptConvert.class);

    /**
     * saveDeptDTO convert sysDeptDO
     *
     * @param saveDeptDTO saveDeptDTO
     * @return sysDeptDO
     */
    @Mappings({})
    SysDept convert(SaveDeptDTO saveDeptDTO);

    /**
     * deptTreeBO convert queryDeptBO
     *
     * @param sysDept 部门信息
     * @return deptTreeBO
     */
    @Mappings({})
    DeptTreeVO convert(SysDept sysDept);


    /**
     * deptTreeBOList convert queryDeptBOList
     *
     * @param deptDOList 部门信息
     * @return deptTreeBOList
     */
    List<DeptTreeVO> convert(List<SysDept> deptDOList);

    /**
     * updateDeptDTO convert sysDeptDO
     *
     * @param updateDeptDTO updateDeptDTO
     * @return sysDeptDO
     */
    @Mappings({})
    SysDept convert(UpdateDeptDTO updateDeptDTO);



}
