package org.training.cloud.system.service.oauth2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.training.cloud.system.entity.oauth2.SysOauth2AccessToken;

import java.util.Objects;


/**
 * token单元测试
 *
 * @author wangtongzhou
 * @since 2023-04-18 22:16
 */
@SpringBootTest
public class Oauth2TokenServiceImplTest {

    @Autowired
    private Oauth2TokenService oauth2TokenService;

    @Value("${system.database.name}")
    private String systemDatabaseName;

    @Test
    public void queryAccessTokenByAccessToken() {
        String name= systemDatabaseName;
        SysOauth2AccessToken sysOauth2AccessToken =
                oauth2TokenService.queryAccessTokenByAccessToken("94b8278b-e904-4231-bd19-5b571e4db7dc");
        Assertions.assertTrue(Objects.nonNull(sysOauth2AccessToken));
    }
}