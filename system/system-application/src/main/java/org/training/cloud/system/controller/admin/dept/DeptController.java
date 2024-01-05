package org.training.cloud.system.controller.admin.dept;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.training.cloud.common.core.vo.CommonResponse;
import org.training.cloud.system.convert.dept.DeptConvert;
import org.training.cloud.system.dto.dept.AddDeptDTO;
import org.training.cloud.system.dto.dept.DeptDTO;
import org.training.cloud.system.dto.dept.ModifyDeptDTO;
import org.training.cloud.system.entity.dept.SysDept;
import org.training.cloud.system.service.dept.DeptService;
import org.training.cloud.system.vo.dept.DeptTreeVO;
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
@RequestMapping("/sys/admin")
@Tag(name ="部门信息")
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
    public CommonResponse<List<DeptVO>> deptList(@RequestBody  DeptDTO deptDTO) {
        List<SysDept> depts = deptService.getAllDept(deptDTO);
        return CommonResponse.ok(DeptConvert.INSTANCE.convert(depts));
    }

    /**
     * 删除部门
     *
     * @param id
     * @return
     */
    @DeleteMapping("/dept")
    @Operation(summary = "删除部门")
    public CommonResponse<?> delDept(@RequestParam Long id) {
        deptService.removeDeptById(id);
        return CommonResponse.ok();
    }

}
