package org.training.cloud.system.controller.oauth2;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.training.cloud.common.core.vo.CommonResponse;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.common.security.core.annotations.NotAuthentication;
import org.training.cloud.system.convert.oauth2.Oauth2ClientConvert;
import org.training.cloud.system.dto.oauth2.AddOauth2ClientDTO;
import org.training.cloud.system.dto.oauth2.ModifyOauth2ClientDTO;
import org.training.cloud.system.dto.oauth2.PageOauth2ClientDTO;
import org.training.cloud.system.entity.oauth2.SysOauth2Client;
import org.training.cloud.system.service.oauth2.Oauth2ClientService;
import org.training.cloud.system.vo.oauth2.Oauth2ClientVO;

/**
 * Oauth2客户端管理
 *
 * @author wangtongzhou 18635604249
 * @since 2023-04-02 09:08
 */
@Api("Oauth2客户端管理")
@RestController
@RequestMapping("/sys/oauth2client")
@NotAuthentication
public class Oauth2ClientController {

    @Autowired
    private Oauth2ClientService oauth2ClientService;

    @PostMapping("/add")
    @ApiOperation("添加Oauth2客户端")
    public CommonResponse<?> addOauth2Client(@RequestBody @Validated AddOauth2ClientDTO addOauth2ClientDTO) {
        oauth2ClientService.addOauth2Client(addOauth2ClientDTO);
        return CommonResponse.ok();
    }


    @PutMapping("/modify")
    @ApiOperation("修改Oauth2客户端")
    public CommonResponse<?> modifyOauth2Client(@RequestBody @Validated ModifyOauth2ClientDTO modifyOauth2ClientDTO) {
        oauth2ClientService.modifyOauth2Client(modifyOauth2ClientDTO);
        return CommonResponse.ok();
    }


    @DeleteMapping("/remove")
    @ApiOperation("删除Oauth2客户端")
    public CommonResponse<?> removeOauth2Client(@RequestParam("id") Long id) {
        oauth2ClientService.removeOauth2Client(id);
        return CommonResponse.ok();
    }


    @GetMapping("/query")
    @ApiOperation("查询Oauth2单个客户端")
    public CommonResponse<Oauth2ClientVO> queryOauth2Client(@RequestParam("id") Long id) {
        SysOauth2Client sysOauth2Client =oauth2ClientService.queryOauth2Client(id);
        return CommonResponse.ok(Oauth2ClientConvert.INSTANCE.convert(sysOauth2Client));
    }


    @GetMapping("/page")
    @ApiOperation("分页查询Oauth2客户端")
    public CommonResponse<PageResponse<Oauth2ClientVO>> queryOauth2Client(@Validated PageOauth2ClientDTO pageOauth2ClientDTO) {
        PageResponse<SysOauth2Client> sysOauth2ClientPageResponse = oauth2ClientService.pageOauth2Client(pageOauth2ClientDTO);
        return CommonResponse.ok(Oauth2ClientConvert.INSTANCE.convert(sysOauth2ClientPageResponse));
    }


}
