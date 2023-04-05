package org.training.cloud.system.service.oauth2;

import org.training.cloud.common.web.core.vo.PageResponse;
import org.training.cloud.system.dto.oauth2.AddOauth2ClientDTO;
import org.training.cloud.system.dto.oauth2.ModifyOauth2ClientDTO;
import org.training.cloud.system.dto.oauth2.PageOauth2ClientDTO;
import org.training.cloud.system.entity.oauth2.SysOauth2Client;

/**
 * Oauth2客户端服务
 *
 * @author wangtongzhou 18635604249
 * @since 2023-04-02 09:13
 */
public interface Oauth2ClientService {


    /**
     * 新增授权客户端
     *
     * @param addOauth2ClientDTO
     */
    void addOauth2Client(AddOauth2ClientDTO addOauth2ClientDTO);

    /**
     * 修改授权客户端
     *
     * @param modifyOauth2ClientDTO
     */
    void modifyOauth2Client(ModifyOauth2ClientDTO modifyOauth2ClientDTO);

    /**
     * 删除授权客户端
     *
     * @param id
     */
    void removeOauth2Client(Long id);

    /**
     * 查询单个授权客户端
     *
     * @param id
     * @return
     */
    SysOauth2Client queryOauth2Client(Long id);



    PageResponse<SysOauth2Client> pageOauth2Client(PageOauth2ClientDTO pageOauth2ClientDTO);
}
