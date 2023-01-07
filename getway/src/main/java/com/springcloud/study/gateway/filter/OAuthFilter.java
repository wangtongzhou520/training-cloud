package com.springcloud.study.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 认证filter
 *
 * @author wangtongzhou
 * @since 2020-09-06 16:35
 */
public class OAuthFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        //如果token认证放行
        if (StringUtils.startsWith(request.getRequestURI(), "/token")) {
            return null;
        }
        //如果网站登录认证方式放行
        String authHeader = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(authHeader)) {
            return null;
        }
        //如果非oath bearer认证方式放行
        if (!StringUtils.startsWithIgnoreCase(authHeader,"bearer ")){
            return null;
        }

        //获取token信息
        //将token信息写入到请求头中

        return null;
    }
}
