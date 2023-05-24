package org.training.cloud.system.controller.admin.oauth2;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.training.cloud.common.core.vo.CommonResponse;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.system.dto.oauth2.Oauth2ScopeCodeDTO;
import org.training.cloud.system.service.oauth2.Oauth2ScopeCodeService;
import org.training.cloud.system.vo.oauth2.Oauth2ScopeCodeVO;

/**
 * 授权资源的管理
 *
 * @author wangtongzhou 
 * @since 2023-04-16 15:47
 */
@Tag(name = "Oauth2 资源管理")
@RestController
@RequestMapping("/sys/oauth2/scope")
public class Oauth2ScopeCodeController {

    @Autowired
    private Oauth2ScopeCodeService oauth2ScopeCodeService;


    @PostMapping("/page")
    @Operation(summary = "分页获取token信息")
    public CommonResponse<PageResponse<Oauth2ScopeCodeVO>> pageAccessToken(@RequestBody Oauth2ScopeCodeDTO oauth2ScopeCodeDTO) {
        return CommonResponse.ok(oauth2ScopeCodeService.pageOauth2ScopeCode(oauth2ScopeCodeDTO));
    }


}
