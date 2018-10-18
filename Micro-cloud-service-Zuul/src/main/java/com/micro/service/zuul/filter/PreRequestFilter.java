package com.micro.service.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;


@Component
public class PreRequestFilter extends ZuulFilter {

    private static final Logger LOG = LoggerFactory.getLogger(PreRequestFilter.class);


    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;//int值来定义过滤器的执行顺序，数值越小优先级越高
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

//    @Override
//    public Object run() {
//        RequestContext ctx = RequestContext.getCurrentContext();
//        HttpServletRequest request = ctx.getRequest();
//        LOG.info("send {} request to {}",request.getMethod(),request.getRequestURL().toString());
//        return null;
//    }

    @Override
    public  Object run(){
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        LOG.info("send {} request to {}",request.getMethod(),request.getRequestURL().toString());
      //  LOG.info(  request.getUserPrincipal().toString());
        LOG.info( request.getServletContext().getContextPath());
        LOG.info( request.getContextPath());
//        String accessToken = request.getParameter("access_token");
//        OAuth2AccessToken oauth2AccessToken = Oauth2Utils.checkTokenInOauth2Client((accessToken!=null)?accessToken:"0");
//
//        ctx.setSendZuulResponse(true);   // 对该请求进行路由

        return  null;
    }

}
