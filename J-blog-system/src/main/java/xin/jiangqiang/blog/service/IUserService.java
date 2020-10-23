package xin.jiangqiang.blog.service;

import xin.jiangqiang.blog.entities.User;
import com.baomidou.mybatisplus.extension.service.IService;
import xin.jiangqiang.blog.req.PasswordREQ;
import xin.jiangqiang.blog.util.base.Result;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author jiangqiang--blog.jiangqiang.xin
 * @since 2020-10-13
 */
public interface IUserService extends IService<User> {
    Result addUser(User user);

    Result isFirstReg();

    Result login(String account, String password);

    Result update(int id, User user);

    /**
     * 更新密码
     *
     * @param req
     * @return
     */
    Result updatePassword(PasswordREQ req);


    /**
     * 获取用户信息
     *
     * @param userId
     * @return
     */
    Result getUserInfo(Integer userId);
}
