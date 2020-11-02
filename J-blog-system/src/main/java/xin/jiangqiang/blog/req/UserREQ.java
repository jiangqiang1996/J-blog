package xin.jiangqiang.blog.req;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author jiangqiang--blog.jiangqiang.xin
 * @since 2020-10-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "User注册对象", description = "用户注册")
@Accessors(chain = true)
public class UserREQ implements Serializable {

    @ApiModelProperty(value = "用户账号，不可修改", required = true)
    @TableField("userAccount")
    private String userAccount;

    @ApiModelProperty(value = "用户名，默认与帐号相同", required = false)
    @TableField("userName")
    private String userName;

    @ApiModelProperty(value = "用户密码",required = true)
    @TableField("userPassword")
    private String userPassword;

    @ApiModelProperty(hidden = true, value = "用户角色，管理员：adminRole，普通用户：defaultRole，访客用户：visitorRole")
    @TableField("userRole")
    private String userRole;

}
