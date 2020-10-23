package xin.jiangqiang.blog.portal.interceptor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import xin.jiangqiang.blog.entities.Article;
import xin.jiangqiang.blog.entities.Jnl;
import xin.jiangqiang.blog.entities.Rule;
import xin.jiangqiang.blog.service.IArticleService;
import xin.jiangqiang.blog.service.IJnlService;
import xin.jiangqiang.blog.service.IRuleService;
import xin.jiangqiang.blog.util.tools.DateUtil;
import xin.jiangqiang.blog.util.tools.NetworkUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 统计文章访问量
 *
 * @author JiangQiang
 * @date 2020/10/20 10:51
 */
@Component
public class ArticleAccessInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    IJnlService jnlService;
    @Autowired
    IArticleService articleService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String IP = NetworkUtil.getIpAddr(request);
        String URI = request.getRequestURI();
        QueryWrapper<Jnl> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("remoteAddr", IP);
        queryWrapper.gt("accessTime", DateUtil.beforeHours(System.currentTimeMillis(), 4));
        queryWrapper.eq("URI", URI);
        List<Jnl> jnlList = jnlService.list(queryWrapper);
        if (CollectionUtils.isEmpty(jnlList)) {//四小时内该用户没有访问该文章的记录
            String[] tmp = URI.split("/");
            Integer articleId = Integer.valueOf(tmp[tmp.length - 1]);
            Article article = articleService.getById(articleId);
            if (article != null && article.getArticleStatus() == 0) {//有文章且有访问权限
                article.setArticleViewCount(article.getArticleViewCount() + 1);
                articleService.saveOrUpdate(article);
            }
        }
        return true;
    }
}