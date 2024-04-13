package org.training.cloud.system.service.oauth2;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.training.cloud.common.core.exception.BusinessException;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.system.convert.oauth2.Oauth2ClientConvert;
import org.training.cloud.system.dao.oauth2.Oauth2ClientMapper;
import org.training.cloud.system.dto.oauth2.AddOauth2ClientDTO;
import org.training.cloud.system.dto.oauth2.ModifyOauth2ClientDTO;
import org.training.cloud.system.dto.oauth2.Oauth2ClientDTO;
import org.training.cloud.system.entity.oauth2.SysOauth2Client;
import org.training.cloud.system.enums.oauth2.Oauth2ClientStateEnum;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Objects;

import static org.training.cloud.system.constant.SystemExceptionEnumConstants.*;

/**
 * Oauth2客户端服务
 *
 * @author wangtongzhou
 * @since 2023-04-02 09:14
 */
@Service
public class Oauth2ClientServiceImpl implements Oauth2ClientService {

    @Resource
    private Oauth2ClientMapper oauth2ClientMapper;

    @Override
    public void addOauth2Client(AddOauth2ClientDTO addOauth2ClientDTO) {
        //检查client_id是否存在
        checkClientIdExists(addOauth2ClientDTO.getClientId());
        //插入
        SysOauth2Client sysOauth2Client = Oauth2ClientConvert.INSTANCE.convert(addOauth2ClientDTO);
        oauth2ClientMapper.insert(sysOauth2Client);
        //TODO 待补充缓存信息
    }

    @Override
    public void modifyOauth2Client(ModifyOauth2ClientDTO modifyOauth2ClientDTO) {
        //检查id是否存在
        checkExists(modifyOauth2ClientDTO.getId());
        //检查client_id是否存在
        checkClientIdExists(modifyOauth2ClientDTO.getClientId());
        //更新
        SysOauth2Client sysOauth2Client = Oauth2ClientConvert.INSTANCE.convert(modifyOauth2ClientDTO);
        oauth2ClientMapper.updateById(sysOauth2Client);
        //TODO 缓存
    }

    @Override
    public void removeOauth2Client(Long id) {
        //检查id是否存在
        SysOauth2Client sysOauth2Client = checkExists(id);
        //更新
        sysOauth2Client.setState(Oauth2ClientStateEnum.DISABLE.getCode());
        oauth2ClientMapper.updateById(sysOauth2Client);
        //TODO 刷新缓存
    }

    @Override
    public SysOauth2Client queryOauth2Client(Long id) {
        return oauth2ClientMapper.selectById(id);
    }

    @Override
    public SysOauth2Client queryOauth2ClientByClientId(String clientId) {
        SysOauth2Client sysOauth2Client = oauth2ClientMapper.selectByClientId(clientId);
        //检查是否存在
        if (Objects.isNull(sysOauth2Client)) {
            throw new BusinessException(OAUTH2_CLIENT_NOT_EXIST);
        }
        //检查客户端状态
        if (Oauth2ClientStateEnum.DISABLE.getCode().equals(sysOauth2Client.getState())) {
            throw new BusinessException(OAUTH2_CLIENT_DISABLE);
        }
        return sysOauth2Client;
    }

    @Override
    public PageResponse<SysOauth2Client> pageOauth2Client(Oauth2ClientDTO oauth2ClientDTO) {
        return oauth2ClientMapper.selectPage(oauth2ClientDTO);
    }

    @Override
    public SysOauth2Client checkOauth2Client(String clientId, String clientSecret, String grantType, Collection<String> scopes, String redirectUri) {
        SysOauth2Client sysOauth2Client = queryOauth2ClientByClientId(clientId);
        if (Objects.isNull(sysOauth2Client)) {
            throw new BusinessException(OAUTH2_CLIENT_EXIST);
        }
        //状态检查
        if (Oauth2ClientStateEnum.DISABLE.getCode().equals(sysOauth2Client.getState())) {
            throw new BusinessException(OAUTH2_CLIENT_DISABLE);
        }
        //客户端密钥检查
        if (StringUtils.isNotBlank(clientSecret) && !StringUtils.equals(sysOauth2Client.getClientSecret(), clientSecret)) {
            throw new BusinessException(OAUTH2_CLIENT_SECRET_ERROR);
        }
        //授权模式检查
        if (StringUtils.isNotBlank(grantType)&&!sysOauth2Client.getGrantTypes().contains(grantType)){
            throw new BusinessException(OAUTH2_CLIENT_AUTHORIZED_GRANT_TYPE_NOT_EXISTS);
        }
        //授权范围检查
        if (CollectionUtils.isNotEmpty(scopes)&&!CollectionUtils.containsAll(scopes,
                sysOauth2Client.getScopes())){
            throw new BusinessException(OAUTH2_CLIENT_SCOPE_NOT_EXISTS);
        }
        //重定向地址检查
        if (StringUtils.isNotBlank(redirectUri)&&!StringUtils.startsWith(redirectUri,
                sysOauth2Client.getRedirectUrl())){
            throw new BusinessException(OAUTH2_CLIENT_REDIRECT_URI_NOT_MATCH);
        }
        return sysOauth2Client;
    }

    private SysOauth2Client checkExists(Long id) {
        SysOauth2Client sysOauth2Client = oauth2ClientMapper.selectById(id);
        if (Objects.isNull(sysOauth2Client)) {
            throw new BusinessException(OAUTH2_CLIENT_NOT_EXIST);
        }
        return sysOauth2Client;
    }

    private void checkClientIdExists(String clientId) {
        SysOauth2Client sysOauth2Client = oauth2ClientMapper.selectByClientId(clientId);
        if (Objects.isNull(sysOauth2Client)) {
            throw new BusinessException(OAUTH2_CLIENT_EXIST);
        }
    }
}
