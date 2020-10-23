package xin.jiangqiang.blog.req;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author JiangQiang
 * @date 2020/10/19 9:58
 */
@Data
@Api("规则相关请求封装")
public class RuleREQ {
    @ApiModelProperty(value = "规则名字,自定义其他规则时必传，footer和header规则不用传")
    private String ruleKey;

    @ApiModelProperty(value = "规则内容", required = true)
    private String ruleValue;

    @ApiModelProperty(value = "规则解释，描述", required = false)
    private String ruleDescription;

}
