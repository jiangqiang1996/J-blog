package xin.jiangqiang.blog.req;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xin.jiangqiang.blog.util.base.BaseRequest;

/**
 * @author JiangQiang
 * @date 2020/10/15 9:42
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Api("查询文章时的请求对象")
public class QryArticleREQ<T> extends BaseRequest<T> {
    @ApiModelProperty(value = "查询指定状态的文章：文章状态，0：已发布，1：草稿，2：已删除", required = true)
    private Integer articleStatus;
}
