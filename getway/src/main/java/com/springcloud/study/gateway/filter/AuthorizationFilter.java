package org.training.cloud.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 授权过滤器
 *
 * @author wangtongzhou
 * @since 2020-09-06 17:25
 */
public class AuthorizationFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 3;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //获取请求头信息
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        //判断当前的请求是否需要权限认证
        if (isAuth(request)) {
            //获取token信息
            String token = (String) request.getAttribute("token");
            if (StringUtils.isNotBlank(token)) {
                //判断当前用户是否拥有权限
                if (!hasPermission(token, request)) {
                    //异常
                    //记录审计日志
                    handlerError(403, requestContext);
                }
                //增加用户相关信息方便下游数据使用
                requestContext.addZuulRequestHeader("user","");
            } else {
                //异常
                //记录审计日志
                handlerError(401, requestContext);
            }
        }
        return null;
    }

    private void handlerError(int i, RequestContext requestContext) {
        requestContext.getResponse().setContentType("application/json");
        requestContext.setResponseStatusCode(i);
        requestContext.setResponseBody(null);
        requestContext.setSendZuulResponse(false);

    }

    private boolean hasPermission(String token, HttpServletRequest request) {
        return true;
    }

    private boolean isAuth(HttpServletRequest request) {
        return true;
    }
}
