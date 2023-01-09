package com.springcloud.study.system.controller;

import com.springcloud.study.common.core.vo.CommonResponse;
import com.springcloud.study.system.convert.dept.SysDeptConvert;
import com.springcloud.study.system.dto.dept.SaveDeptDTO;
import com.springcloud.study.system.dto.dept.UpdateDeptDTO;
import com.springcloud.study.system.service.dept.SysDeptService;
import com.springcloud.study.system.vo.dept.DeptTreeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.dromara.soul.client.springcloud.annotation.SoulSpringCloudClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门接口
 *
 * @author wangtongzhou
 * @since 2020-06-03 20:22
 */
@RestController
@RequestMapping("/sys")
@Api("部门信息")
@SoulSpringCloudClient(path = "/sys/**")
public class DeptController {

    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 添加部门
     *
     * @param saveDeptDTO saveDeptDTO
     * @return 返回信息
     */
    @PostMapping("/dept")
    @ApiOperation("添加部门信息")
    public CommonResponse<?> saveDept(@RequestBody @Validated SaveDeptDTO saveDeptDTO) {
        sysDeptService.saveDept(saveDeptDTO);
        return CommonResponse.ok();
    }

    /**
     * 修改部门信息
     *
     * @param updateDeptDTO updateDeptDTO
     * @return true or false
     */
    @PutMapping("/dept")
    @ApiOperation("修改部门信息")
    public CommonResponse<?> updateDept(@RequestBody UpdateDeptDTO updateDeptDTO) {
        sysDeptService.updateDept(updateDeptDTO);
        return CommonResponse.ok();
    }

    /**
     * 查询部门树
     *
     * @return 部门树
     */
    @GetMapping("/depts")
    @ApiOperation("部门树")
    public CommonResponse<List<DeptTreeVO>> queryDeptTree() {
        List<DeptTreeVO> treeVoList = sysDeptService.deptTrees();
        return CommonResponse.ok(treeVoList);
    }

    /**
     * 删除部门
     *
     * @param id id
     * @return true or false
     */
    @DeleteMapping("/dept")
    @ApiOperation("部门树")
    public CommonResponse<?> delDept(@RequestBody Integer id) {
        sysDeptService.delDeptById(id);
        return CommonResponse.ok();
    }

}
