package xin.jiangqiang.blog.portal.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import xin.jiangqiang.blog.entities.Article;
import xin.jiangqiang.blog.req.QryArticleByTagREQ;
import xin.jiangqiang.blog.service.IArticleService;
import xin.jiangqiang.blog.util.base.BaseRequest;
import xin.jiangqiang.blog.util.base.Result;

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author jiangqiang--blog.jiangqiang.xin
 * @since 2020-10-13
 */
@Api("对文章的查询，此接口不需要验证用户身份，此接口会有访问统计功能")
@RestController
@RequestMapping("/portal/article")
@CacheConfig(cacheNames = "portalArticle")
public class PortalArticleController {
    @Autowired
    IArticleService articleService;

    @ApiOperation("根据文章id查询文章详细信息")
    @GetMapping("/id/{articleId}")
    @Cacheable(key = "#articleId", condition = "#articleId!=null")
    public Result queryArticleInfo(@PathVariable("articleId") Integer articleId) {
        return articleService.getByIdForPortal(articleId, true);
    }

    @ApiOperation("查询已发布文章列表，支持分页查询，不传参数则查询所有已发布文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "页码"),
            @ApiImplicitParam(name = "size", value = "每页显示多少条")
    })
    @PostMapping("/list")
    public Result listArticles(@RequestBody(required = false) BaseRequest<Article> req) {
        return articleService.listForPortal(req, true);
    }

    @ApiOperation("根据标签分类查询文章，支持分页查询，只能查询已发布的文章")
    @PostMapping("/listByTag")
    public Result listByTagArticles(@RequestBody QryArticleByTagREQ<Article> req) {
        return articleService.listByTag(req, true, true);
    }
}
