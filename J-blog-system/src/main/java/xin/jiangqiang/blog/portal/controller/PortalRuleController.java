package xin.jiangqiang.blog.portal.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
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
@Api("获取管理端配置的规则，一些内定规则只支持查询，具体有哪些规则可以通过查询列表获取")
@RestController
@RequestMapping("/portal/rule")
//表示所有方法都支持缓存，也可以用在具体每一个方法上
//@Cacheable(cacheNames = "rule")
@CacheConfig(cacheNames = "rule")//指定在类上，其他方法上就不需要写缓存名
public class PortalRuleController {
    @Autowired
    IRuleService ruleService;

    @ApiOperation("用于获取设置的header,footer信息,以及其他定制信息")
    @GetMapping("/{ruleKey}")
    @ApiImplicitParam(name = "ruleKey", value = "根据规则名字查询规则内容和相关描述", required = true)
    @Cacheable(key = "#ruleKey", condition = "#ruleKey!=null")
    public Result custom(@PathVariable("ruleKey") String ruleKey) {
        return ruleService.jGetById(ruleKey);
    }

    @ApiOperation("查询规则列表")
    @GetMapping("/list")
    @Cacheable(key = "\"list\"")
    public Result list() {
        return ruleService.jList();
    }

}
