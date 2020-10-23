package xin.jiangqiang.blog.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import xin.jiangqiang.blog.util.config.JblogProperties;
import xin.jiangqiang.blog.entities.Jnl;
import xin.jiangqiang.blog.service.IJnlService;
import xin.jiangqiang.blog.service.IRuleService;
import xin.jiangqiang.blog.util.tools.NetworkUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 记录所有请求访问日志
 *
 * @author JiangQiang
 * @date 2020/10/20 10:51
 */
@Component
public class BlogAccessInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    IJnlService jnlService;
    @Autowired
    IRuleService ruleService;

    /**
     * 记录所有接口访问日志
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String IP = NetworkUtil.getIpAddr(request);
        Integer flag;
        if (requestURI != null && requestURI.startsWith(request.getContextPath() + "/admin/")) {
            flag = 0;//后台
        } else if (requestURI != null && requestURI.startsWith(request.getContextPath() + "/portal/")) {
            flag = 1;//前台
        } else if (requestURI != null && requestURI.startsWith(request.getContextPath() + jblogProperties.getFileResources().getPathPattern().replace("**", ""))) {
            flag = 2;//文件
        } else if (requestURI != null && requestURI.startsWith(request.getContextPath() + jblogProperties.getStaticResources().getPathPattern().replace("**", ""))) {
            flag = 3;//静态资源
        } else if (requestURI != null && requestURI.equals(request.getContextPath() + "/error")) {
            flag = 4;//出错
        } else {
            flag = -1;//其他
        }
        Long accessTime = System.currentTimeMillis();
        Jnl jnl = new Jnl(null, requestURI, IP, flag, accessTime);
        jnlService.save(jnl);
        return true;
    }

    @Autowired
    private JblogProperties jblogProperties;
}