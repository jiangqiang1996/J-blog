package xin.jiangqiang.blog;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author JiangQiang
 * @date 2020/10/4 8:29
 */
@SpringBootApplication
//开启swagger接口文档
@EnableSwagger2Doc
//开启事务管理
@EnableTransactionManagement
//开启缓存
@EnableCaching
public class AppStart {
    public static void main(String[] args) {
        SpringApplication.run(AppStart.class);
    }
}
