package com.springcloud.study.system.service.dept;

import com.springcloud.study.system.bo.dept.DeptTreeBO;
import com.springcloud.study.system.dto.dept.SaveDeptDTO;
import com.springcloud.study.system.dto.dept.UpdateDeptDTO;

import java.util.List;

/**
 * 部门服务
 *
 * @author wangtongzhou
 * @since 2020-06-15 20:19
 */
public interface SysDeptService {

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
    List<DeptTreeBO> deptTrees();

    /**
     * 删除部门信息
     *
     * @param id deptId
     */
    void delDeptById(Integer id);
}
