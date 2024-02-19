package org.training.cloud.system.convert.dept;

import com.google.common.collect.Lists;
import org.training.cloud.system.dto.dept.AddDeptDTO;
import org.training.cloud.system.dto.dept.ModifyDeptDTO;
import org.training.cloud.system.entity.dept.SysDept;
import org.training.cloud.system.entity.user.SysUser;
import org.training.cloud.system.vo.dept.DeptTreeVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.training.cloud.system.vo.dept.DeptVO;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * sysDeptDTO convert sysDeptDO
 *
 * @author wangtongzhou
 * @since 2020-06-15 21:13
 */
@Mapper
public interface DeptConvert {

    DeptConvert INSTANCE = Mappers.getMapper(DeptConvert.class);

    /**
     * saveDeptDTO convert sysDeptDO
     *
     * @param addDeptDTO saveDeptDTO
     * @return sysDeptDO
     */
    @Mappings({})
    SysDept convert(AddDeptDTO addDeptDTO);


    @Mappings({})
    DeptVO convert(SysDept sysDept);

    /**
     * @param sysDeptList
     * @return
     */
    List<DeptVO> convert(List<SysDept> sysDeptList);

    /**
     * updateDeptDTO convert sysDeptDO
     *
     * @param modifyDeptDTO updateDeptDTO
     * @return sysDeptDO
     */
    @Mappings({})
    SysDept convert(ModifyDeptDTO modifyDeptDTO);

    /**
     * convert
     *
     * @param sysDeptList
     * @param sysUserList
     */
    default List<DeptVO> convert(List<SysDept> sysDeptList, List<SysUser> sysUserList) {
        List<DeptVO> result = Lists.newArrayList();
        Map<Long, SysUser> sysUserMap = sysUserList.stream()
                .collect(Collectors.toMap(SysUser::getId, x -> x));
        sysDeptList.forEach(x -> {
            DeptVO deptVO = convert(x);
            SysUser sysUser = sysUserMap.get(x.getManageId());
            if (Objects.nonNull(sysUser)) {
                deptVO.setManageName(sysUser.getUsername());
            }
            result.add(deptVO);
        });
        return result;
    }
}
