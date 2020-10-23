package xin.jiangqiang.blog.admin.interceptor;

import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import xin.jiangqiang.blog.entities.User;
import xin.jiangqiang.blog.service.IUserService;
import xin.jiangqiang.blog.util.base.Result;
import xin.jiangqiang.blog.util.enums.ResultEnum;
import xin.jiangqiang.blog.util.tools.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 认证拦截器
 */
@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private IUserService userService;

    /**
     * Controller方法处理完之前
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 是否登录
        boolean isLogin = false;

        // 请求头带上令牌 Authorization: Bearer jwtToken
        final String authHeader = request.getHeader("Authorization");

        if (StringUtils.isNotBlank(authHeader) && authHeader.startsWith("Bearer ")) {
            // 截取token，
            final String token = authHeader.substring(7);
            // 解析token
            Claims claims = jwtUtil.parseJWT(token);
            if (claims != null) {
                // 是否登录
                Boolean b = (Boolean) claims.get("isLogin");
                Long time = (Long) claims.get("createTime");
                User user = userService.getById(claims.getId());
                if (b && user != null && user.getUserRefreshTime().equals(time)) {
                    request.setAttribute("userId", claims.getId());
                    // 已经登录，放行请求
                    isLogin = true;
                }
            }
        }

        if (!isLogin) {
            // 未登录，则响应信息
            response.setContentType("application/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(401);
            response.getWriter().write(Result.build(ResultEnum.UNAUTHENTICATED).toJsonString());
        }
        // 不放行
        return isLogin;
    }

    /**
     * Controller方法处理完之后
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

}
