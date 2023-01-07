package com.springcloud.study.test1.controller;

import com.springcloud.study.common.core.vo.CommonResponse;
import com.springcloud.study.system.vo.dept.DeptTreeVO;
import com.springcloud.study.test1.service.UserService;
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
