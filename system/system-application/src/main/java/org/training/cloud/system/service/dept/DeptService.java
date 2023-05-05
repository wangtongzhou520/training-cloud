package org.training.cloud.system.service.dept;

import org.training.cloud.system.dto.dept.SaveDeptDTO;
import org.training.cloud.system.dto.dept.UpdateDeptDTO;
import org.training.cloud.system.vo.dept.DeptTreeVO;

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
     * @param saveDeptDTO 添加部门
     */
    void saveDept(SaveDeptDTO saveDeptDTO);


    /**
     * 更新用户信息
     *
     * @param updateDeptDTO 用户信息
     */
    void updateDept(UpdateDeptDTO updateDeptDTO);


    /**
     * 返回部门树信息
     *
     * @return 部门树
     */
    List<DeptTreeVO> deptTrees();

    /**
     * 删除部门信息
     *
     * @param id deptId
     */
    void delDeptById(Integer id);
}
