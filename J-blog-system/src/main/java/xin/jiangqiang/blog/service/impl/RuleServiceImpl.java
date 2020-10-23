package xin.jiangqiang.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang.StringUtils;
import xin.jiangqiang.blog.entities.Rule;
import xin.jiangqiang.blog.mapper.RuleMapper;
import xin.jiangqiang.blog.service.IRuleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xin.jiangqiang.blog.util.base.Result;

/**
 * <p>
 * 关于博客系统的规则设置以及各种配置信息。 服务实现类
 * </p>
 *
 * @author jiangqiang--blog.jiangqiang.xin
 * @since 2020-10-15
 */
@Service
public class RuleServiceImpl extends ServiceImpl<RuleMapper, Rule> implements IRuleService {
    @Override
    public Result jSaveOrUpdate(Rule rule) {
        if (rule != null) {
            if (StringUtils.isBlank(rule.getRuleKey())) {
                return Result.error("规则名字不能为空");
            }
            if (StringUtils.isBlank(rule.getRuleValue())) {
                return Result.error("规则内容不能为空");
            }
            rule.setRuleStatus(0);
            Rule tmpRule = getById(rule);
            Result result = checkRuleKey(rule.getRuleKey());
            if (result != null) {
                return result;
            }
            if (tmpRule == null) {
                if (save(rule)) {
                    return Result.ok("添加规则成功");
                } else {
                    return Result.error("添加规则失败");
                }
            } else {
                if (tmpRule.getRuleStatus() == 0) {
                    if (updateById(rule)) {
                        return Result.ok("修改规则成功");
                    } else {
                        return Result.error("修改规则失败");
                    }
                } else {
                    return Result.error("禁止修改此规则");
                }
            }
        } else {
            return Result.error("规则参数未传或丢失");
        }
    }

    /**
     * 禁止修改的rule
     *
     * @param ruleKey
     * @return
     */
    public static Result checkRuleKey(String ruleKey) {
        if ("list".equals(ruleKey)) {
            return Result.error("规则名字非法");
        }
        if ("allViewCount".equals(ruleKey)) {
            return Result.error("规则名字非法");
        }
        return null;
    }

    @Override
    public Result jGetById(String ruleKey) {
        Rule tmpRule = getById(ruleKey);
        if (tmpRule != null && tmpRule.getRuleStatus() == 0) {
            return Result.ok(tmpRule);
        } else {
            return Result.error("查询失败");
        }
    }

    @Override
    public Result jList() {
        QueryWrapper<Rule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ruleStatus", 0);
        return Result.ok(list(queryWrapper));
    }
}
