package xin.jiangqiang.blog.admin.controller;

import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xin.jiangqiang.blog.entities.User;
import xin.jiangqiang.blog.req.PasswordREQ;
import xin.jiangqiang.blog.service.IUserService;
import xin.jiangqiang.blog.util.base.Result;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author jiangqiang--blog.jiangqiang.xin
 * @since 2020-10-12
 */
@RestController
@RequestMapping("/admin/user")
public class UserController {
    @Autowired
    IUserService userService;

    @ApiOperation("判断博客系统是否第一次使用，第一次使用需要跳转到注册页面注册，本博客目前仅支持注册一个用户")
    @GetMapping("/isFirstReg")
    public Result isFirstReg() {
        return userService.isFirstReg();
    }

    @ApiOperation("注册，只能注册一个用户，需要先调用上一个接口查询能否注册")
    @PostMapping("/reg")
    public Result reg(@RequestBody User user) {
        return userService.addUser(user);
    }

    @ApiOperation("登录，成功后返回token")
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        return userService.login(user.getUserAccount(), user.getUserPassword());
    }

    @GetMapping("/info")
    @ApiOperation("查询用户信息")
    public Result getUserInfo(@RequestAttribute(value = "userId") Integer userId) {
        return userService.getUserInfo(userId);
    }

    @ApiOperation("退出时必须调用此接口,让token失效")
    @PostMapping("/logout")
    public Result logout(@RequestAttribute(value = "userId") Integer userId) {
        User user = new User();
        user.setId(userId);
        user.setUserRefreshTime(System.currentTimeMillis());
        if (userService.updateById(user)) {
            return Result.ok("退出成功");
        } else {
            return Result.error("退出失败");
        }
    }

    @ApiOperation("修改用户密码")
    @PutMapping("/pwd")
    public Result updatePwd(@RequestBody PasswordREQ req) {
        return userService.updatePassword(req);
    }

    @ApiOperation("修改用户信息,此接口暂时只能修改用户名和用户头像")
    @PutMapping("/userInfo")
    public Result updateUserInfo(@RequestBody User user) {
        if (user == null || user.getId() == null) {
            return Result.error("用户id不能为空");
        }
        if (StringUtils.isBlank(user.getUserName()) && StringUtils.isBlank(user.getUserAvatar())) {
            return Result.error("没有需要修改的数据");
        }
        return userService.update(user.getId(), user);
    }
}
