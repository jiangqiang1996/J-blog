package xin.jiangqiang.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import xin.jiangqiang.blog.util.config.JblogProperties;
import xin.jiangqiang.blog.entities.User;
import xin.jiangqiang.blog.mapper.UserMapper;
import xin.jiangqiang.blog.req.PasswordREQ;
import xin.jiangqiang.blog.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xin.jiangqiang.blog.util.base.Result;
import xin.jiangqiang.blog.util.tools.JwtUtil;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author jiangqiang--blog.jiangqiang.xin
 * @since 2020-10-13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private JblogProperties jblogProperties;

    @Override
    public Result addUser(User user) {
        List<User> list = list();
        if (list.size() < 0) {
            return Result.error("禁止注册");
        } else {
            if (user == null || StringUtils.isBlank(user.getUserAccount())) {
                return Result.error("帐号不能为空");
            }
            if (StringUtils.isBlank(user.getUserPassword())) {
                return Result.error("密码不能为空");
            }
            //如果用户名为空,默认以账号作为用户名
            if (StringUtils.isBlank(user.getUserName())) {
                user.setUserName(user.getUserAccount());
            }
            //默认角色就是管理员
            if (StringUtils.isBlank(user.getUserRole())) {
                user.setUserRole("adminRole");
            }
            //获取类路径下的图片作为用户默认头像,类路径下具体哪一张图片可以配置
            ClassPathResource classPathResource = new ClassPathResource(jblogProperties.getDefaultAvatarName());
            byte[] bytes = null;
            try {
                bytes = FileUtils.readFileToByteArray(classPathResource.getFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
            user.setUserAvatarData(bytes);

            String password = new BCryptPasswordEncoder().encode(user.getUserPassword());
            user.setUserPassword(password);
            // 3. 提交数据
            boolean b = this.save(user);
            if (b) {
                return Result.ok("注册成功");
            }
            return Result.error("注册失败");
        }
    }

    @Override
    public Result isFirstReg() {
        List<User> list = list();
        if (list.size() == 0) {
            return Result.ok("没有注册过用户,允许注册");
        } else {
            return Result.error("注册过用户,禁止注册");
        }
    }

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Result login(String account, String password) {
        Result error = Result.error("帐号或密码错误");
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            return error;
        }
        // 1. 通过帐号查询数据
        User user = getByAccount(account);
        // 用户不存在
        if (user == null) {
            return error;
        }
        // 2. 存在，判断输入的密码和数据库密码是否一致
        boolean b = new BCryptPasswordEncoder().matches(password, user.getUserPassword());
        if (!b) {
            return error;
        }
        // 3. 生成 token
        Long time = System.currentTimeMillis();
        //将时间插入数据库
        user.setUserPassword(null);
        user.setUserRefreshTime(time);
        if (!this.updateById(user)) {
            return Result.error("登录失败");
        }
        String jwt = jwtUtil.createJWT(user.getId() + "", user.getUserAccount(), true, time);
        // 4. 响应给客户端
        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);
        return Result.ok(map);
    }

    @Override
    public Result update(int id, User user) {
        if (user.getId() == null) {
            user.setId(id);
        }
        user.setUserPassword(null);
        user.setUserAvatarData(null);
        user.setUserRefreshTime(null);
        user.setUserRole(null);
        // 更新操作
        int i = baseMapper.updateById(user);
        if (i < 1) {
            return Result.error("修改用户信息失败");
        }
        return Result.ok();
    }

    @Override
    public Result updatePassword(PasswordREQ req) {
        if (req == null || req.getUserId() == null) {
            return Result.error("用户Id不能为空");
        }
        if (StringUtils.isEmpty(req.getOldUserPassword())) {
            return Result.error("原密码不能为空");
        }
        if (StringUtils.isEmpty(req.getNewUserPassword())) {
            return Result.error("新密码不能为空");
        }
        if (StringUtils.isEmpty(req.getConfirmNewUserPassword())) {
            return Result.error("请再次确认新密码");
        }
        if (req.getOldUserPassword().equals(req.getNewUserPassword())) {
            return Result.error("新密码不能和原密码相同");
        }
        if (!req.getNewUserPassword().equals(req.getConfirmNewUserPassword())) {
            return Result.error("两次密码不一致");
        }
        // 通过用户id查询用户信息
        User user = baseMapper.selectById(req.getUserId());
        if (user == null) {
            return Result.error("没有该用户");
        }
        // 判断输入的密码是否正确
        boolean b = new BCryptPasswordEncoder().matches(req.getOldUserPassword(), user.getUserPassword());
        if (!b) {
            return Result.error("原密码错误");
        }
        user.setUserPassword(new BCryptPasswordEncoder().encode(req.getNewUserPassword()));
        user.setUserRefreshTime(System.currentTimeMillis());
        if (updateById(user)) {
            return Result.ok("密码修改成功,请重新登录");
        } else {
            return Result.error("密码修改失败");
        }
    }

    public User getByAccount(String account) {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("userAccount", account);
        return baseMapper.selectOne(query);
    }

    @Override
    public Result getUserInfo(Integer userId) {
        User user = getById(userId);
        if (user == null) {
            return Result.error("用户不存在");
        }
        String encodeToString = Base64.getEncoder().encodeToString(user.getUserAvatarData());
        System.out.println(encodeToString);
        // 将密码设置null,不把密码返回前端
        user.setUserPassword(null);
        return Result.ok(user);
    }

}
