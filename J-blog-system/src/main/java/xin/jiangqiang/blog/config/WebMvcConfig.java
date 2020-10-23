package xin.jiangqiang.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xin.jiangqiang.blog.admin.interceptor.AuthenticationInterceptor;
import xin.jiangqiang.blog.interceptor.BlogAccessInterceptor;
import xin.jiangqiang.blog.portal.interceptor.ArticleAccessInterceptor;
import xin.jiangqiang.blog.portal.interceptor.PortalAccessInterceptor;
import xin.jiangqiang.blog.util.config.JblogProperties;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;
    @Autowired
    private PortalAccessInterceptor portalAccessInterceptor;
    @Autowired
    private ArticleAccessInterceptor articleAccessInterceptor;
    @Autowired
    private BlogAccessInterceptor blogAccessInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //1.加入的顺序就是拦截器执行的顺序，
        //2.按顺序执行所有拦截器的preHandle
        //3.所有的preHandle 执行完再执行全部postHandle 最后是postHandle
        registry.addInterceptor(authenticationInterceptor)
                // 拦截所有请求
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/user/isFirstReg")
                .excludePathPatterns("/admin/user/reg")
                .excludePathPatterns("/admin/user/login");

        /**
         * 访问前台页面触发
         * 统计系统访问量
         */
        registry.addInterceptor(portalAccessInterceptor)
                .addPathPatterns("/portal/**");
        /**
         * 访问文章详情触发
         * 统计具体某文章访问量
         */
        registry.addInterceptor(articleAccessInterceptor)
                .addPathPatterns("/portal/article/id/**");

        /**
         * 只能放最后
         * 记录访问日志
         * 访问任何接口触发
         */
        registry.addInterceptor(blogAccessInterceptor)
                .addPathPatterns("/**");
    }

    @Autowired
    private JblogProperties jblogProperties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(jblogProperties.getStaticResources().getPathPattern())
//                .addResourceLocations("/")
//                .addResourceLocations("classpath:/")//会访问到项目配置文件
                .addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/public/");
        registry.addResourceHandler(jblogProperties.getFileResources().getPathPattern())
                .addResourceLocations("file:" + jblogProperties.getFileResources().getPath());
    }
}
