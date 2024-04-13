package org.training.cloud.system.service.dept;

import org.training.cloud.system.dto.dept.AddDeptDTO;
import org.training.cloud.system.dto.dept.DeptDTO;
import org.training.cloud.system.dto.dept.ModifyDeptDTO;
import org.training.cloud.system.entity.dept.SysDept;
import org.training.cloud.system.vo.dept.DeptVO;

import java.util.Collection;
import java.util.List;

/**
 * 部门服务
 *
 * @author wangtongzhou
 * @since 2020-06-15 20:19
 */
public interface DeptService {

    /**
     * 保存部门信息
     *
     * @param addDeptDTO 添加部门
     */
    void addDept(AddDeptDTO addDeptDTO);


    /**
     * 更新用户信息
     *
     * @param modifyDeptDTO 用户信息
     */
    void modifyDept(ModifyDeptDTO modifyDeptDTO);



    /**
     * 删除部门信息
     *
     * @param id
     */
    void removeDeptById(Long id);

    /**
     * 获取部门信息
     *
     * @param id
     * @return
     */
    SysDept getDeptId(Long id);

    /**
     * 查询部门列表
     *
     * @param ids
     * @return
     */
    List<SysDept> getDeptListByIds(Collection<Long> ids);

    /**
     * 部门列表
     *
     * @return
     */
    List<DeptVO> getAllDept(DeptDTO deptDTO);

}
