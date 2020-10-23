package xin.jiangqiang.blog.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.*;
import xin.jiangqiang.blog.entities.Article;
import xin.jiangqiang.blog.entities.TagArticle;
import xin.jiangqiang.blog.req.ArticleREQ;
import xin.jiangqiang.blog.req.QryArticleByTagREQ;
import xin.jiangqiang.blog.req.QryArticleREQ;
import xin.jiangqiang.blog.service.IArticleService;
import xin.jiangqiang.blog.util.base.Result;

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author jiangqiang--blog.jiangqiang.xin
 * @since 2020-10-13
 */
@Api("对文章的增删改查")
@RestController
@RequestMapping("/admin/article")
public class ArticleController {
    @Autowired
    IArticleService articleService;

    @ApiOperation("新建或修改文章,如果传入的文章id存在,则会修改文章,不存在或不传则会新建文章")
    @PostMapping("/edit")
    @Caching(evict = {
            @CacheEvict(cacheNames = {"portalArticle"}, key = "#articleREQ.id", condition = "#articleREQ.id!=null")
    })
    public Result saveArticle(@RequestBody ArticleREQ articleREQ, @RequestAttribute(value = "userId") Integer userId) {

        return articleService.saveArticle(articleREQ, userId);
    }

    @ApiOperation("编辑指定文章")
    @PutMapping("/id/{articleId}")
    @Caching(evict = {
            @CacheEvict(cacheNames = {"portalArticle"}, key = "#articleId", condition = "#articleId!=null")
    })
    public Result updateArticle(@PathVariable("articleId") Integer articleId, @RequestBody ArticleREQ articleREQ, @RequestAttribute(value = "userId") Integer userId) {
        articleREQ.setId(articleId);
        return articleService.saveArticle(articleREQ, userId);
    }

    @ApiOperation("根据文章id查询文章详细信息")
    @GetMapping("/id/{articleId}")
    public Result queryArticleInfo(@PathVariable("articleId") Integer articleId) {
        Article article = articleService.getById(articleId);
        if (article == null) {
            return Result.error("没有查询到文章信息");
        }
        return Result.ok(article);
    }

    @ApiOperation("根据文章id删除文章")
    @DeleteMapping("/id/{articleId}")
    @Caching(evict = {
            @CacheEvict(cacheNames = {"portalArticle"}, key = "#articleId", condition = "#articleId!=null")
    })
    public Result deleteArticle(@PathVariable("articleId") Integer articleId) {
        return articleService.deleteById(articleId);
    }

    @ApiOperation("根据文章id从垃圾箱找回文章")
    @PutMapping("/reset/{articleId}")
    @Caching(evict = {
            @CacheEvict(cacheNames = {"portalArticle"}, key = "#articleId", condition = "#articleId!=null")
    })
    public Result resetArticle(@PathVariable("articleId") Integer articleId) {
        return articleService.resetById(articleId);
    }

    @ApiOperation("根据文章id发布文章")
    @PutMapping("/publish/{articleId}")
    @Caching(evict = {
            @CacheEvict(cacheNames = {"portalArticle"}, key = "#articleId", condition = "#articleId!=null")
    })
    public Result publishArticle(@PathVariable("articleId") Integer articleId) {
        return articleService.publishById(articleId);
    }

    @ApiOperation("查询文章列表，支持分页查询，以及查询不同状态的文章，不传参数则查询所有文章")
    @PostMapping("/list")
    public Result listArticles(@RequestBody(required = false) QryArticleREQ<Article> req) {
        return articleService.list(req);
    }

    @ApiOperation("根据标签分类查询文章，支持分页查询，只能查询已发布的文章")
    @PostMapping("/listByTag")
    public Result listByTagArticles(@RequestBody(required = true) QryArticleByTagREQ<Article> req) {
        return articleService.listByTag(req, false, false);
    }
}
