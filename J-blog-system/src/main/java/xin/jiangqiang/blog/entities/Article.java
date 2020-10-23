package xin.jiangqiang.blog.entities;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 文章表
 * </p>
 *
 * @author jiangqiang--blog.jiangqiang.xin
 * @since 2020-10-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("j_blog_article")
@ApiModel(value = "Article对象", description = "文章表")
@Accessors(chain = true)
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "标签Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "文章标题")
    @TableField("articleTitle")
    private String articleTitle;

    @ApiModelProperty(value = "文章摘要 Markdown")
    @TableField("articleAbstract")
    private String articleAbstract;

    @ApiModelProperty(value = "文章摘要纯文本")
    @TableField("articleAbstractText")
    private String articleAbstractText;

    @ApiModelProperty(value = "文章标签，英文逗号分隔")
    @TableField("articleTags")
    private String articleTags;

    @ApiModelProperty(value = "文章作者 id")
    @TableField("articleAuthorId")
    private Integer articleAuthorId;

    @ApiModelProperty(value = "文章浏览计数")
    @TableField("articleViewCount")
    private Integer articleViewCount;

    @ApiModelProperty(value = "文章正文内容")
    @TableField("articleContent")
    private String articleContent;

    @ApiModelProperty(value = "文章是否置顶")
    @TableField("articlePutTop")
    private Boolean articlePutTop;

    @ApiModelProperty(value = "文章创建时间戳")
    @TableField("articleCreated")
    private Long articleCreated;

    @ApiModelProperty(value = "文章更新时间戳")
    @TableField("articleUpdated")
    private Long articleUpdated;

    @ApiModelProperty(value = "文章状态，0：已发布，1：草稿，2：已删除")
    @TableField("articleStatus")
    private Integer articleStatus;


}
