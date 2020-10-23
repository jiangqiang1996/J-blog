package xin.jiangqiang.blog.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.models.auth.In;
import xin.jiangqiang.blog.entities.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 文章表 Mapper 接口
 * </p>
 *
 * @author jiangqiang--blog.jiangqiang.xin
 * @since 2020-10-13
 */
public interface ArticleMapper extends BaseMapper<Article> {
    List<Article> listByTag(IPage<Article> page, Integer tagId, Integer... articleStatusList);
}
