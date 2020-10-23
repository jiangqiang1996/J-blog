package xin.jiangqiang.blog.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JiangQiang
 * @date 2019/3/29 14:13
 */
@Configuration
public class DruidConfig {
    //绑定数据源配置
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druid() {
        return new DruidDataSource();
    }

    /**
     * 配置一个druid的监控:
     * 1.配置一个druid的后台 管理Servlet
     * 2.配置一个druid的filter
     */
    @Bean
    public ServletRegistrationBean statViewServlet() {
        //所有/druid/的请求都会访问这个servlet
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        Map<String, String> initParam = new HashMap<>();
        initParam.put(StatViewServlet.PARAM_NAME_USERNAME, "suixin");
        initParam.put(StatViewServlet.PARAM_NAME_PASSWORD, "suixin");
        //如果不写，则默认所有ip都可以访问
        initParam.put(StatViewServlet.PARAM_NAME_ALLOW, "");
        //这个ip的主机无法访问后台
        initParam.put(StatViewServlet.PARAM_NAME_DENY, "");//第二个参数是ip
        bean.setInitParameters(initParam);
        return bean;
    }

    @Bean
    public FilterRegistrationBean filter() {
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new WebStatFilter());
        Map<String, String> initParam = new HashMap<>();
        initParam.put(WebStatFilter.PARAM_NAME_EXCLUSIONS, "*.js,*.css,/druid/*");
        bean.setInitParameters(initParam);
        //设置拦截请求
        bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;
    }
}
