package org.training.cloud.test1.controller;

import org.training.cloud.common.core.vo.CommonResponse;
import org.training.cloud.system.vo.dept.DeptTreeVO;
import org.training.cloud.test1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * feign test
 *
 * @author wangtongzhou
 * @since 2020-12-18 13:40
 */
@RestController
public class OpenFeignTestController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public CommonResponse<List<DeptTreeVO>> test() {
        CommonResponse<List<DeptTreeVO>> deptTree = userService.queryDeptTree();
        return deptTree;
    }
}
