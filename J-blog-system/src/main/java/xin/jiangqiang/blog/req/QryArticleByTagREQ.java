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
@EqualsAndHashCode(callSuper = true)
@Data
@Api("查询文章时的请求对象")
public class QryArticleByTagREQ<T> extends BaseRequest<T> {
    @ApiModelProperty(value = "根据标签分类查询文章", required = true)
    private Integer tagId;
}
