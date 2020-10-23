package xin.jiangqiang.blog.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author JiangQiang
 * @date 2020/10/13 16:27
 */
@Data
public class ArticleREQ {
    @ApiModelProperty(value = "文章唯一标识符，仅编辑文章时传,如果不传,则为新增文章")
    private Integer id;

    @ApiModelProperty(value = "文章标题")
    private String articleTitle;

    @ApiModelProperty(value = "文章摘要，Markdown文本")
    private String articleAbstract;

    @ApiModelProperty(value = "文章摘要纯文本，html文本")
    private String articleAbstractText;

    @ApiModelProperty(value = "文章标签，英文逗号分隔")
    private String articleTags;

    @ApiModelProperty(value = "文章正文内容")
    private String articleContent;

    @ApiModelProperty(value = "文章是否置顶")
    private Boolean articlePutTop;

    @ApiModelProperty(value = "查询指定状态的文章：文章状态，0：已发布，1：草稿，2：已删除")
    private Integer articleStatus;
}
