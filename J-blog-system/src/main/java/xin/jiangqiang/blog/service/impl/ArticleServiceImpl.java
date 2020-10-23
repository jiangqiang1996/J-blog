package xin.jiangqiang.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import xin.jiangqiang.blog.entities.Article;
import xin.jiangqiang.blog.entities.TagArticle;
import xin.jiangqiang.blog.mapper.ArticleMapper;
import xin.jiangqiang.blog.req.ArticleREQ;
import xin.jiangqiang.blog.req.QryArticleByTagREQ;
import xin.jiangqiang.blog.req.QryArticleREQ;
import xin.jiangqiang.blog.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xin.jiangqiang.blog.service.ITagArticleService;
import xin.jiangqiang.blog.service.ITagService;
import xin.jiangqiang.blog.util.base.BaseRequest;
import xin.jiangqiang.blog.util.base.Result;
import xin.jiangqiang.blog.util.tools.Markdowns;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author jiangqiang--blog.jiangqiang.xin
 * @since 2020-10-13
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    @Autowired
    ITagService tagService;
    @Autowired
    ITagArticleService tagArticleService;

    @Override
    public Result saveArticle(ArticleREQ articleREQ, Integer id) {
        Article article = null;
        if (articleREQ.getId() != null) {
            article = baseMapper.selectById(articleREQ.getId());
        }
        if (article != null) {//修改
            if (StringUtils.isNotBlank(articleREQ.getArticleTitle())) {
                article.setArticleTitle(articleREQ.getArticleTitle());
            }
            if (StringUtils.isNotBlank(articleREQ.getArticleAbstract())) {
                article.setArticleAbstract(articleREQ.getArticleAbstract());
            }
            if (StringUtils.isNotBlank(articleREQ.getArticleAbstractText())) {
                article.setArticleAbstractText(articleREQ.getArticleAbstractText());
            }
            if (StringUtils.isNotBlank(articleREQ.getArticleContent())) {
                article.setArticleContent(articleREQ.getArticleContent());
            }
            if (articleREQ.getArticlePutTop() != null) {
                article.setArticlePutTop(articleREQ.getArticlePutTop());
            }
            if (articleREQ.getArticleStatus() != null) {
                article.setArticleStatus(articleREQ.getArticleStatus());
            }
            article.setArticleUpdated(System.currentTimeMillis());
            article.setArticleTags(articleREQ.getArticleTags());
            tagService.saveOrUpdateTagString(articleREQ.getArticleTags());
            if (saveOrUpdate(article)) {
                tagArticleService.saveOrUpdateString(article.getArticleTags(), article.getId());
                return Result.ok("修改成功");
            } else {
                return Result.error("修改失败");
            }

        } else {//新增
            article = new Article();
            if (StringUtils.isBlank(articleREQ.getArticleTitle())) {
                return Result.error("标题不能为空");
            }
            if (StringUtils.isBlank(articleREQ.getArticleAbstract())) {
                return Result.error("摘要Markdown不能为空");
            }
            if (StringUtils.isBlank(articleREQ.getArticleAbstractText())) {
                return Result.error("摘要纯文本不能为空");
            }
            if (StringUtils.isBlank(articleREQ.getArticleContent())) {
                return Result.error("内容不能为空");
            }
            if (articleREQ.getArticlePutTop() == null) {
                article.setArticlePutTop(false);
            } else {
                article.setArticlePutTop(articleREQ.getArticlePutTop());
            }
            if (articleREQ.getArticleStatus() == null) {
                article.setArticleStatus(1);
            } else {
                article.setArticleStatus(articleREQ.getArticleStatus());
            }
            article.setArticleTitle(articleREQ.getArticleTitle());
            article.setArticleAbstract(articleREQ.getArticleAbstract());
            article.setArticleAbstractText(articleREQ.getArticleAbstractText());
            article.setArticleContent(articleREQ.getArticleContent());
            article.setArticleTags(articleREQ.getArticleTags());
            tagService.saveOrUpdateTagString(articleREQ.getArticleTags());
            Long times = System.currentTimeMillis();
            article.setArticleCreated(times);
            article.setArticleUpdated(times);
            article.setArticleAuthorId(id);
            article.setArticleViewCount(0);
            if (save(article)) {
                tagArticleService.saveOrUpdateString(article.getArticleTags(), article.getId());
                return Result.ok("保存成功");
            } else {
                return Result.error("保存失败");
            }
        }
    }

    @Override
    public Result list(QryArticleREQ<Article> req) {
        if (req == null) {//没有查询条件
            return Result.list(list());
        } else {
            if (req.getCurrent() == null && req.getSize() == null) {
                if (req.getArticleStatus() == null) {//没有查询条件
                    return Result.list(list());
                } else {
                    QueryWrapper<Article> wrapper = new QueryWrapper<>();
                    // 文章状态，0：已发布，1：草稿，2：已删除
                    wrapper.in("articleStatus", req.getArticleStatus());
                    return Result.list(list(wrapper));
                }
            } else if (req.getCurrent() != null && req.getSize() != null) {
                QueryWrapper<Article> wrapper = new QueryWrapper<>();
                if (req.getArticleStatus() != null) {
                    wrapper.in("articleStatus", req.getArticleStatus());
                }
                return Result.ok(page(req.getPage(), wrapper));
            } else {
                return Result.error("参数错误");
            }
        }
    }

    /**
     * req不可能为null
     *
     * @param req
     * @param onlyPublish
     * @param isToHtml
     * @return
     */
    @Override
    public Result listByTag(QryArticleByTagREQ<Article> req, Boolean onlyPublish, Boolean isToHtml) {
        Result result = BaseRequest.checkFields(req);
        if (result != null) {
            return result;
        }
        if (req.getTagId() == null) {
            return Result.error("请传入tagId");
        }
        IPage<Article> page = req.getPage();
        List<Article> articleList;
        if (onlyPublish) {
            articleList = baseMapper.listByTag(page, req.getTagId(), 0);
        } else {
            articleList = baseMapper.listByTag(page, req.getTagId(), 0, 1, 2);
        }
        if (isToHtml) {
            articleList = toHtml(articleList);
        }
        if (page != null) {
            page.setRecords(articleList);
            return Result.ok(page);
        } else {
            return Result.ok(articleList);
        }
    }

    List<Article> toHtml(List<Article> articles) {
        for (Article article : articles) {
            article = toHtml(article);
        }
        return articles;
    }

    @Autowired
    Markdowns markdowns;
    Article toHtml(Article article) {
        return article.setArticleContent(markdowns.toHtml(article.getArticleContent()));
    }

    @Override
    public Result deleteById(Integer id) {
        Article article = getById(id);
        article.setArticleStatus(2);
        if (updateById(article)) {
            return Result.ok("删除成功");
        } else {
            return Result.error("删除失败");
        }
    }

    @Override
    public Result resetById(Integer id) {
        Article article = getById(id);
        article.setArticleStatus(1);
        if (updateById(article)) {
            return Result.ok("找回成功");
        } else {
            return Result.error("找回失败");
        }
    }

    @Override
    public Result publishById(Integer id) {
        Article article = getById(id);
        article.setArticleStatus(0);
        if (updateById(article)) {
            return Result.ok("发布成功");
        } else {
            return Result.error("发布失败");
        }
    }

    @Override
    public Result getByIdForPortal(Integer id, Boolean isToHtml) {
        Article article = getById(id);
        if (article == null) {
            return Result.error("没有查询到文章信息");
        }
        if (article.getArticleStatus() != 0) {
            return Result.error("没有该文章的查询权限");
        }
        if (isToHtml) {
            article = toHtml(article);
        }
        return Result.ok(article);
    }

    @Override
    public Result listForPortal(BaseRequest<Article> req, Boolean isToHtml) {
        Result result = BaseRequest.checkFields(req);
        if (result != null) {//分页条件合法或为空
            return result;
        }
        if (req.getPage() != null) {
            QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("articleStatus", 0);
            IPage<Article> articleIPage = page(req.getPage(), queryWrapper);
            List<Article> articleList = articleIPage.getRecords();
            if (isToHtml) {
                articleList = toHtml(articleList);
                articleIPage.setRecords(articleList);
            }
            return Result.ok(articleIPage);
        } else {
            List<Article> articleList = listForPublish();
            if (isToHtml) {
                articleList = toHtml(articleList);
            }
            return Result.list(articleList);
        }
    }

    private List<Article> listForPublish() {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("articleStatus", 0);
        return list(queryWrapper);
    }
}
