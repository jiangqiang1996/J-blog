package xin.jiangqiang.blog.util.tools;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import xin.jiangqiang.blog.util.config.JblogProperties;

import java.util.Date;

/**
 * 生成和验证jwt的工具类
 */

@Data
@Component
public class JwtUtil {
    @Autowired
    private JblogProperties jblogProperties;


    /**
     * 生成JWT
     *
     * @param id
     */
    public String createJWT(String id, String subject, Boolean isLogin, Long time) {
        JwtBuilder builder = Jwts.builder()
                .setId(id)//用户id
                .setSubject(subject)//一般是用户名
                .setIssuedAt(new Date())//签发令牌的时间
                .signWith(SignatureAlgorithm.HS256, jblogProperties.getJwtConfig().getSecretKey())//算法，密钥
                .claim("isLogin", isLogin)//是否登录
                .claim("createTime", time);//token生成时间
        if (jblogProperties.getJwtConfig().getExpires() > 0) {
            // expires乘以1000是毫秒转秒
            builder.setExpiration(new Date(System.currentTimeMillis() + jblogProperties.getJwtConfig().getExpires() * 1000));//过期时间
        }
        return builder.compact();
    }

    /**
     * 解析JWT
     *
     * @param jwtToken
     */
    public Claims parseJWT(String jwtToken) {
        return Jwts.parser().setSigningKey(jblogProperties.getJwtConfig().getSecretKey()).parseClaimsJws(jwtToken).getBody();
    }

}
