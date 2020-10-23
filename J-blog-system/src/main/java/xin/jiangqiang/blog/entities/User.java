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
 * 用户表
 * </p>
 *
 * @author jiangqiang--blog.jiangqiang.xin
 * @since 2020-10-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("j_blog_user")
@ApiModel(value = "User对象", description = "用户信息修改时的请求对象")
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户Id", required = true)
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户账号，不可修改", required = false)
    @TableField("userAccount")
    private String userAccount;

    @ApiModelProperty(value = "用户名，默认与帐号相同", required = true)
    @TableField("userName")
    private String userName;

    @ApiModelProperty(value = "用户密码", hidden = true)
    @TableField("userPassword")
    private String userPassword;

    @ApiModelProperty(hidden = true, value = "用户角色，管理员：adminRole，普通用户：defaultRole，访客用户：visitorRole")
    @TableField("userRole")
    private String userRole;

    @ApiModelProperty(value = "用户头像图片链接地址，如果此处为空，则前端使用userAvatarData的数据生成图片")
    @TableField("userAvatar")
    private String userAvatar;

    @ApiModelProperty(value = "用户默认头像数据,用户不可修改")
    @TableField("userAvatarData")
    private byte[] userAvatarData;

    @ApiModelProperty(value = "用户刷新时间", hidden = true)
    @TableField("userRefreshTime")
    private Long userRefreshTime;

}
