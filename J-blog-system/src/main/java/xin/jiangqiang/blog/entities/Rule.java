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

/**
 * <p>
 * 关于博客系统的规则设置以及各种配置信息。
 * </p>
 *
 * @author jiangqiang--blog.jiangqiang.xin
 * @since 2020-10-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("j_blog_rule")
@ApiModel(value = "Rule对象", description = "关于博客系统的规则设置以及各种配置信息，暂时只能修改或新增，不允许删除。")
@AllArgsConstructor
@NoArgsConstructor
public class Rule implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "规则名字，规则表的主键")
    @TableId(value = "ruleKey", type = IdType.ASSIGN_ID)
    private String ruleKey;

    @ApiModelProperty(value = "规则内容")
    @TableField("ruleValue")
    private String ruleValue;

    @ApiModelProperty(value = "规则状态,用户新增（修改,删除）的规则只能是0，系统内置规则为1，内置规则只对开发人员可见")
    @TableField("ruleStatus")
    private Integer ruleStatus;

    @ApiModelProperty(value = "规则解释，描述")
    @TableField("ruleDescription")
    private String ruleDescription;


}
