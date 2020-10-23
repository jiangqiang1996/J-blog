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
 * 标签-文章关联表
 * </p>
 *
 * @author jiangqiang--blog.jiangqiang.xin
 * @since 2020-10-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("j_blog_tag_article")
@ApiModel(value="TagArticle对象", description="标签-文章关联表")
@Accessors(chain =true)
public class TagArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "文章 id")
    @TableField("articleId")
    private Integer articleId;

    @ApiModelProperty(value = "标签 id")
    @TableField("tagId")
    private Integer tagId;


}
