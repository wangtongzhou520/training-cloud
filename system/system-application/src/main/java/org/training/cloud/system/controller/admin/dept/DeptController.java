package org.training.cloud.system.controller.admin.dept;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.training.cloud.common.core.vo.CommonResponse;
import org.training.cloud.system.dto.dept.AddDeptDTO;
import org.training.cloud.system.dto.dept.DeptDTO;
import org.training.cloud.system.dto.dept.ModifyDeptDTO;
import org.training.cloud.system.service.dept.DeptService;
import org.training.cloud.system.vo.dept.DeptVO;

import javax.validation.Valid;
import java.util.List;

/**
 * 部门接口
 *
 * @author wangtongzhou
 * @since 2020-06-03 20:22
 */
@RestController
@RequestMapping("/sys")
@Tag(name = "部门信息")
public class DeptController {

    @Autowired
    private DeptService deptService;

    /**
     * 添加部门
     *
     * @param addDeptDTO
     * @return
     */
    @PostMapping("/dept")
    @Operation(summary = "添加部门信息")
    @PreAuthorize("@ssc.hasPermission('sys:dept:create')")
    public CommonResponse<?> saveDept(@RequestBody @Valid AddDeptDTO addDeptDTO) {
        deptService.addDept(addDeptDTO);
        return CommonResponse.ok();
    }

    /**
     * 修改部门信息
     *
     * @param modifyDeptDTO
     * @return
     */
    @PutMapping("/dept")
    @Operation(summary = "修改部门信息")
    @PreAuthorize("@ssc.hasPermission('sys:dept:update')")
    public CommonResponse<?> updateDept(@RequestBody @Valid ModifyDeptDTO modifyDeptDTO) {
        deptService.modifyDept(modifyDeptDTO);
        return CommonResponse.ok();
    }

    /**
     * 查询部门树
     *
     * @return 部门树
     */
    @PostMapping("/deptList")
    @Operation(summary = "部门树")
    @PreAuthorize("@ssc.hasPermission('sys:dept:list')")
    public CommonResponse<List<DeptVO>> deptList(@RequestBody DeptDTO deptDTO) {
        return CommonResponse.ok(deptService.getAllDept(deptDTO));
    }

    /**
     * 删除部门
     *
     * @param id
     * @return
     */
    @DeleteMapping("/dept/{id}")
    @Operation(summary = "删除部门")
    @PreAuthorize("@ssc.hasPermission('sys:dept:delete')")
    public CommonResponse<?> delDept(@PathVariable("id") Long id) {
        deptService.removeDeptById(id);
        return CommonResponse.ok();
    }

}
