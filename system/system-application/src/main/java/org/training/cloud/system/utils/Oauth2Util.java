package org.training.cloud.system.utils;

/**
 * Oauth2工具类
 *
 * @author wangtongzhou
 * @since 2023-05-07 21:10
 */
public class Oauth2Util {

    public static String buildAuthorizationCodeRedirectUrl(String redirectUrl, String authorizationCode, String state) {
        return redirectUrl + "?code=" + authorizationCode + "&state" + state;
    }


    public static String buildImplicitRedirectUrl(String redirectUrl, String accessToken) {
        return redirectUrl + "?access_token=" + accessToken;
    }
}
