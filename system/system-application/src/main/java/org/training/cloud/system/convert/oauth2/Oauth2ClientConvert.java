package org.training.cloud.system.convert.oauth2;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.training.cloud.common.web.core.vo.PageResponse;
import org.training.cloud.system.dto.oauth2.AddOauth2ClientDTO;
import org.training.cloud.system.dto.oauth2.ModifyOauth2ClientDTO;
import org.training.cloud.system.entity.oauth2.SysOauth2Client;
import org.training.cloud.system.vo.oauth2.Oauth2ClientVO;

/**
 * 客户端转化类
 *
 * @author wangtongzhou 
 * @since 2023-04-02 15:06
 */
@Mapper
public interface Oauth2ClientConvert {

    Oauth2ClientConvert INSTANCE = Mappers.getMapper(Oauth2ClientConvert.class);


    /**
     * addOauth2ClientDTO convert SysOauth2Client
     *
     * @param addOauth2ClientDTO
     * @return
     */
    SysOauth2Client convert(AddOauth2ClientDTO addOauth2ClientDTO);


    /**
     * modifyOauth2ClientDTO convert SysOauth2Client
     *
     * @param modifyOauth2ClientDTO
     * @return
     */
    SysOauth2Client convert(ModifyOauth2ClientDTO modifyOauth2ClientDTO);

    /**
     * sysOauth2Client convert Oauth2ClientVO
     *
     * @param sysOauth2Client
     * @return
     */
    Oauth2ClientVO convert(SysOauth2Client sysOauth2Client);


    /**
     * sysOauth2ClientPageResponse convert PageResponse<Oauth2ClientVO>
     *
     * @param sysOauth2ClientPageResponse
     * @return
     */
    PageResponse<Oauth2ClientVO> convert(PageResponse<SysOauth2Client> sysOauth2ClientPageResponse);
}
