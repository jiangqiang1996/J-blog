package xin.jiangqiang.blog.service;

import org.springframework.transaction.annotation.Transactional;
import xin.jiangqiang.blog.entities.Rule;
import com.baomidou.mybatisplus.extension.service.IService;
import xin.jiangqiang.blog.util.base.Result;

/**
 * <p>
 * 关于博客系统的规则设置以及各种配置信息。 服务类
 * </p>
 *
 * @author jiangqiang--blog.jiangqiang.xin
 * @since 2020-10-15
 */
public interface IRuleService extends IService<Rule> {
    @Transactional
    Result jSaveOrUpdate(Rule rule);

    @Transactional
    Result jGetById(String ruleKey);

    @Transactional
    Result jList();
}
