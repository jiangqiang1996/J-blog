package xin.jiangqiang.blog.portal.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import xin.jiangqiang.blog.entities.Tag;
import xin.jiangqiang.blog.entities.TagArticle;
import xin.jiangqiang.blog.service.ITagArticleService;
import xin.jiangqiang.blog.service.ITagService;
import xin.jiangqiang.blog.util.base.Result;

import java.util.List;

/**
 * <p>
 * 标签表 前端控制器
 * </p>
 *
 * @author jiangqiang--blog.jiangqiang.xin
 * @since 2020-10-13
 */
@Api("文章标签查询操作")
@RestController
@RequestMapping("/portal/tag")
@CacheConfig(cacheNames = "tag")
public class PortalTagController {

    @Autowired
    ITagService tagService;
    @Autowired
    ITagArticleService tagArticleService;

    @ApiOperation("根据标签id查询标签详细信息")
    @GetMapping("/{tagId}")
    @Cacheable(key = "#tagId", condition = "#tagId!=null")
    @ApiImplicitParams({@ApiImplicitParam(name = "tagId", value = "标签Id", required = true)})
    public Result queryArticleInfo(@PathVariable("tagId") Integer tagId) {
        Tag tag = tagService.getById(tagId);
        if (tag == null) {
            return Result.error("没有查询到标签信息");
        }
        return Result.ok(tag);
    }

    @ApiOperation(("查询所有标签"))
    @GetMapping("/list")
    @Cacheable(key = "\"list\"")
    public Result listTags() {
        return Result.ok(tagService.list());
    }
}
