package xin.jiangqiang.blog.admin.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.web.bind.annotation.*;

import xin.jiangqiang.blog.entities.Rule;
import xin.jiangqiang.blog.req.RuleREQ;
import xin.jiangqiang.blog.service.IRuleService;
import xin.jiangqiang.blog.util.base.Result;

/**
 * <p>
 * 关于博客系统的规则设置以及各种配置信息。 前端控制器
 * </p>
 *
 * @author jiangqiang--blog.jiangqiang.xin
 * @since 2020-10-15
 */
@Api("存储博客系统的一些规则,包括系统内置规则和用户自定义规则,部分系统内置规则对于用户不可见,不可修改;用户自定义规则包括博客页面的个性化设置")
@RestController
@RequestMapping("/admin/rule")
@CacheConfig(cacheNames = "rule")
public class RuleController {
    @Autowired
    IRuleService ruleService;

    @ApiOperation("设置页面header信息,可以直接存html代码,用于博客页面引入第三方js插件或样式,每一个博客页面的header部分必须引入这部分内容")
    @PostMapping("/header")
    @ApiImplicitParam(name = "ruleValue", value = "博客页面的header部分,建议存储html内容,用于引入第三方js插件或css样式", required = true)
    @Caching(evict = {
            @CacheEvict(cacheNames = {"rule"}, key = "\"list\""),
            @CacheEvict(cacheNames = {"rule"}, key = "'header'")
    })
    public Result header(@RequestBody RuleREQ ruleREQ) {
        Rule rule = new Rule("header", ruleREQ.getRuleValue(), 0, "博客系统前台页面的header部分配置,可以用于引入样式或第三方js插件");
        return ruleService.jSaveOrUpdate(rule);
    }

    @ApiOperation("设置页面footer信息,可以直接存html代码,用于博客页面底部备案信息或执行js代码,每一个博客页面的页脚部分必须引入这部分内容")
    @PostMapping("/footer")
    @ApiImplicitParam(name = "ruleValue", value = "博客页面的footer部分,建议存储html内容", required = true)
    @Caching(evict = {
            @CacheEvict(cacheNames = {"rule"}, key = "\"list\""),
            @CacheEvict(cacheNames = {"rule"}, key = "'footer'")
    })
    public Result footer(@RequestBody RuleREQ ruleREQ) {
        Rule rule = new Rule("footer", ruleREQ.getRuleValue(), 0, "博客系统前台页面的footer部分配置,可以用于配置备案信息,或执行js代码");
        return ruleService.jSaveOrUpdate(rule);
    }

    @ApiOperation("定制其他信息,此接口主要用于扩展博客系统的页面功能,也可用于长期存储一些数据.例如存储用户的个性化设置.")
    @PostMapping("/custom")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ruleKey", value = "自定义规则的名字,与其他规则名字不可重复", required = true),
            @ApiImplicitParam(name = "ruleValue", value = "自定义规则内容", required = true),
            @ApiImplicitParam(name = "ruleDescription", value = "对于规则作用的描述", required = false)
    })
    @Caching(evict = {
            @CacheEvict(cacheNames = {"rule"}, key = "\"list\""),
            @CacheEvict(cacheNames = {"rule"}, key = "#ruleREQ.ruleKey", condition = "#ruleREQ.ruleKey!=null")
    })
    public Result custom(@RequestBody RuleREQ ruleREQ) {
        Rule rule = new Rule(ruleREQ.getRuleKey(), ruleREQ.getRuleValue(), 0, ruleREQ.getRuleDescription());
        return ruleService.jSaveOrUpdate(rule);
    }

    @ApiOperation("用于获取设置的header,footer信息,以及其他定制信息")
    @GetMapping("/{ruleKey}")
    @ApiImplicitParam(name = "ruleKey", value = "根据规则名字查询规则内容和相关描述", required = true)
    @Cacheable(cacheNames = "rule", key = "#ruleKey", condition = "#ruleKey!=null")
    public Result custom(@PathVariable("ruleKey") String ruleKey) {
        return ruleService.jGetById(ruleKey);
    }

    @ApiOperation("查询规则列表")
    @GetMapping("/list")
    @Cacheable(cacheNames = "rule", key = "\"list\"")
    public Result list() {
        return ruleService.jList();
    }

}
