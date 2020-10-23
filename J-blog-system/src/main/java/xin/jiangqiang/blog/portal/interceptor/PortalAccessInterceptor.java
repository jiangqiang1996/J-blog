package xin.jiangqiang.blog.portal.interceptor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import xin.jiangqiang.blog.entities.Jnl;
import xin.jiangqiang.blog.entities.Rule;
import xin.jiangqiang.blog.service.IJnlService;
import xin.jiangqiang.blog.service.IRuleService;
import xin.jiangqiang.blog.util.tools.DateUtil;
import xin.jiangqiang.blog.util.tools.NetworkUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 博客系统访问量
 *
 * @author JiangQiang
 * @date 2020/10/20 10:51
 */
@Component
public class PortalAccessInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    IJnlService jnlService;
    @Autowired
    IRuleService ruleService;

    /**
     * 统计博客前台页面访问量
     * 过滤重复ip访问量
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String IP = NetworkUtil.getIpAddr(request);
        QueryWrapper<Jnl> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("remoteAddr", IP);
        queryWrapper.gt("accessTime", DateUtil.beforeHours(System.currentTimeMillis(), 4));
        List<Jnl> jnlList = jnlService.list(queryWrapper);
        if (CollectionUtils.isEmpty(jnlList)) {
            Rule rule = ruleService.getById("allViewCount");
            if (rule != null) {//不是第一次插入，肯定有值
                Integer allViewCount = Integer.valueOf(rule.getRuleValue());
                allViewCount++;
                rule.setRuleValue(String.valueOf(allViewCount));
            } else {
                rule = new Rule("allViewCount", "1", 0, "博客系统前台页面总访问量");
            }
            ruleService.saveOrUpdate(rule);
        }
        return true;
    }
}