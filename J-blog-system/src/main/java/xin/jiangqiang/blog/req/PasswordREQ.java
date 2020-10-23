package xin.jiangqiang.blog.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author JiangQiang
 * @date 2020/10/13 14:17
 */
@Data
public class PasswordREQ implements Serializable {

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    /**
     * 原密码
     */
    @ApiModelProperty(value = "原密码")
    private String oldUserPassword;

    /**
     * 新密码
     */
    @ApiModelProperty(value = "新密码")
    private String newUserPassword;

    /**
     * 新密码
     */
    @ApiModelProperty(value = "确认新密码")
    private String confirmNewUserPassword;
}
