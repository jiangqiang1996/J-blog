package xin.jiangqiang.blog.util.tools;

import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xin.jiangqiang.blog.util.base.Result;
import xin.jiangqiang.blog.util.config.JblogProperties;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

/**
 * @author JiangQiang
 * @date 2020/10/9 9:52
 */
@Component
public class Markdowns {
    private static final Logger logger = LoggerFactory.getLogger(Result.class);
    private static final DataHolder OPTIONS = new MutableDataSet().
            set(com.vladsch.flexmark.parser.Parser.EXTENSIONS, Arrays.asList(
                    TablesExtension.create(),
                    TaskListExtension.create(),
                    StrikethroughExtension.create(),
                    AutolinkExtension.create())).
            set(HtmlRenderer.SOFT_BREAK, "<br />\n");
    /**
     * Built-in MD engine parser.
     */
    private static final com.vladsch.flexmark.parser.Parser PARSER =
            com.vladsch.flexmark.parser.Parser.builder(OPTIONS).build();

    /**
     * Built-in MD engine HTML renderer.
     */
    private static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();

    private static String toHtmlByLute(final String markdownText, String luteEngineUrl) throws Exception {
        final URL url = new URL(luteEngineUrl);
        final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(100);
        conn.setReadTimeout(1000);
        conn.setDoOutput(true);

        try (final OutputStream outputStream = conn.getOutputStream()) {
            IOUtils.write(markdownText, outputStream, "UTF-8");
        }

        String ret;
        try (final InputStream inputStream = conn.getInputStream()) {
            ret = IOUtils.toString(inputStream, "UTF-8");
        }

        conn.disconnect();
        logger.warn("==================使用lute进行解析==================");
        return ret;
    }

    private static String toHtmlByFlexmark(final String markdownText) {
        com.vladsch.flexmark.util.ast.Node document = PARSER.parse(markdownText);
        return RENDERER.render(document);
    }

    @Autowired
    JblogProperties jblogProperties;

    /**
     * 默认尝试用lute引擎解析
     * 解析失败后使用Flexmark解析
     *
     * @param markdownText markdown文本
     * @return
     */
    public String toHtml(String markdownText) {
        return toHtml(markdownText, jblogProperties.getMarkdownConfig().getLuteUrl());
    }

    /**
     * url不为空时，会优先使用lute引擎解析
     *
     * @param markdownText  markdown文本
     * @param luteEngineUrl lute引擎的http调用地址
     * @return
     */
    public String toHtml(final String markdownText, String luteEngineUrl) {
        String html = null;
        if (StringUtils.isNotBlank(luteEngineUrl)) {
            try {
                /**
                 * 调用黑客派开源的Markdown 引擎解析
                 */
                html = toHtmlByLute(markdownText, luteEngineUrl);
            } catch (Exception e) {
                logger.warn("Failed to use Lute [" + luteEngineUrl + "] for markdown [md=" + StringUtils.substring(markdownText, 0, 256) + "]: " + e.getMessage());
            }
        }
        /**
         * 解析失败后使用Flexmark解析
         */
        if (StringUtils.isBlank(html)) {
            html = toHtmlByFlexmark(markdownText);
        }
        return html;
    }

}
