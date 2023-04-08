package org.training.cloud.system.dto.oauth2;

import lombok.Data;

import java.io.Serializable;

/**
 * 增加授权客户端
 *
 * @author wangtongzhou 
 * @since 2023-04-05 21:03
 */
@Data
public class AddOauth2AccessTokenDTO implements Serializable {

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 用户类型 0 c端 1 管理端
     */
    private Integer userType;

    /**
     * 客户端id
     */
    private String clientId;



}
