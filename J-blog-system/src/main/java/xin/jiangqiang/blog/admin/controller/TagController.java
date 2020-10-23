package xin.jiangqiang.blog.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
@Api("文章标签相关操作")
@RestController
@RequestMapping("/admin/tag")
@CacheConfig(cacheNames = "tag")
public class TagController {

    @Autowired
    ITagService tagService;
    @Autowired
    ITagArticleService tagArticleService;

    @ApiOperation("根据标签id查询标签详细信息")
    @GetMapping("/{tagId}")
    @ApiImplicitParams({@ApiImplicitParam(name = "tagId", value = "标签Id", required = true)})
    @Cacheable(key = "#tagId", condition = "#tagId!=null")
    public Result queryArticleInfo(@PathVariable("tagId") Integer tagId) {
        Tag tag = tagService.getById(tagId);
        if (tag == null) {
            return Result.error("没有查询到标签信息");
        }
        return Result.ok(tag);
    }

    @ApiOperation("根据标签id删除标签")
    @DeleteMapping("/{tagId}")
    @ApiImplicitParams({@ApiImplicitParam(name = "tagId", value = "标签Id", required = true)})
    @Caching(evict = {
            @CacheEvict(key = "\"list\""),
            @CacheEvict(key = "#tagId",condition = "#tagId!=null")
    })
    public Result deleteArticle(@PathVariable("tagId") Integer tagId) {
        QueryWrapper<TagArticle> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tagId", tagId);
        List<TagArticle> tagArticles = tagArticleService.list(queryWrapper);
        if (tagArticles.size() > 0) {
            return Result.error("此标签已经被使用，不能删除");
        } else {
            if (tagService.removeById(tagId)) {
                return Result.ok();
            } else {
                return Result.error("删除失败");
            }
        }
    }

    @ApiOperation("根据标签id修改标签")
    @PutMapping("/{tagId}")
    @ApiImplicitParams({@ApiImplicitParam(name = "tagId", value = "标签Id", required = true), @ApiImplicitParam(name = "tagTitle", value = "标签标题", required = true)})
    @Caching(evict = {
            @CacheEvict(key = "\"list\""),
            @CacheEvict(key = "#tagId",condition = "#tagId!=null")
    })
    public Result updateArticle(@PathVariable("tagId") Integer tagId, @RequestBody String tagTitle) {
        if (tagService.updateById(new Tag().setTagTitle(tagTitle))) {
            return Result.ok();
        } else {
            return Result.error("删除失败");
        }
    }

    @ApiOperation(("查询所有标签"))
    @GetMapping("/list")
    @Cacheable(key = "\"list\"")
    public Result listTags() {
        return Result.ok(tagService.list());
    }
}
