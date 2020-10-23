package xin.jiangqiang.blog.service;

import org.springframework.transaction.annotation.Transactional;
import xin.jiangqiang.blog.entities.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import xin.jiangqiang.blog.entities.TagArticle;
import xin.jiangqiang.blog.req.ArticleREQ;
import xin.jiangqiang.blog.req.QryArticleByTagREQ;
import xin.jiangqiang.blog.req.QryArticleREQ;
import xin.jiangqiang.blog.util.base.BaseRequest;
import xin.jiangqiang.blog.util.base.Result;

import java.util.List;

/**
 * <p>
 * 文章表 服务类
 * </p>
 *
 * @author jiangqiang--blog.jiangqiang.xin
 * @since 2020-10-13
 */
public interface IArticleService extends IService<Article> {
    @Transactional
    Result saveArticle(ArticleREQ articleREQ, Integer id);

    Result list(QryArticleREQ<Article> req);

    Result listByTag(QryArticleByTagREQ<Article> req, Boolean onlyPublish, Boolean isToHtml);

    /**
     * 根据id删除文章
     *
     * @param id
     * @return
     */
    Result deleteById(Integer id);

    /**
     * 从垃圾箱找回文章
     *
     * @param id
     * @return
     */
    Result resetById(Integer id);

    /**
     * 发布文章
     *
     * @param id
     * @return
     */
    Result publishById(Integer id);

    /**
     * 查询文章，只查询正常发布的文章
     *
     * @param id
     * @return
     */
    Result getByIdForPortal(Integer id, Boolean isToHtml);

    /**
     * 查询文章列表，只查询正常发布的文章
     *
     * @param
     * @return
     */
    Result listForPortal(BaseRequest<Article> req, Boolean isToHtml);
}
