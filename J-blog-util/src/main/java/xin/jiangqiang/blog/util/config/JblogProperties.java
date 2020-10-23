package xin.jiangqiang.blog.util.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import xin.jiangqiang.blog.util.tools.Markdowns;

/**
 * 统一读取配置文件中的数据
 *
 * @author JiangQiang
 * @date 2020/10/13 11:32
 */
@Data
@Component
@ConfigurationProperties("j-blog")
public class JblogProperties {
    private String defaultAvatarName;
    private FileResourcesConfig fileResources;
    private StaticResourcesConfig staticResources;
    private JwtConfig jwtConfig;
    private MarkdownConfig markdownConfig;
    /**
     * 上传文件配置
     */
    @Data
    public static class FileResourcesConfig {
        private String path;
        private String cachePath;
        private String pathPattern;
    }

    /**
     * 项目静态资源配置
     */
    @Data
    public static class StaticResourcesConfig {
        private String pathPattern;
    }

    /**
     * jwt配置
     */
    @Data
    public static class JwtConfig {
        // 密钥
        private String secretKey;
        //单位秒，默认7天
        private long expires = 60 * 60 * 24 * 7;
    }

    /**
     * markdown配置
     */
    @Data
    public static class MarkdownConfig {
        private String luteUrl;
    }
}
