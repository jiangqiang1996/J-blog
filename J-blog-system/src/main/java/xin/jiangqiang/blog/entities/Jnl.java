package xin.jiangqiang.blog.entities;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author jiangqiang--blog.jiangqiang.xin
 * @since 2020-10-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("j_blog_jnl")
@ApiModel(value = "Jnl对象", description = "日志表")
@Accessors(chain = true)
public class Jnl implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键标识")
    @TableId(value = "Id", type = IdType.AUTO)
    private Integer Id;

    @ApiModelProperty(value = "访问路径")
    @TableField("URI")
    private String uri;

    @ApiModelProperty(value = "访问者IP地址")
    @TableField("remoteAddr")
    private String remoteAddr;

    @ApiModelProperty(value = "0表示后台接口访问，1表示博客前台接口访问，2表示文件访问，3表示静态资源访问，4表示出错，-1表示其他")
    private Integer flag;

    @ApiModelProperty(value = "用户访问时间戳")
    @TableField("accessTime")
    private Long accessTime;

}
