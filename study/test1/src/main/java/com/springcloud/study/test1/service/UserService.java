package com.springcloud.study.test1.service;

import com.springcloud.study.common.core.vo.CommonResponse;
import com.springcloud.study.system.vo.dept.DeptTreeVO;
import com.springcloud.study.test1.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 用户相关服务
 *
 * @author wangtongzhou
 * @since 2020-12-18 21:50
 */
@FeignClient(value = "system", configuration = FeignConfig.class)
@RequestMapping("/sys")
public interface UserService {

    @GetMapping("/depts")
    CommonResponse<List<DeptTreeVO>> queryDeptTree();

}
