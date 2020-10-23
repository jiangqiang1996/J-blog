package xin.jiangqiang.blog.mapper;

import xin.jiangqiang.blog.entities.TagArticle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 标签-文章关联表 Mapper 接口
 * </p>
 *
 * @author jiangqiang--blog.jiangqiang.xin
 * @since 2020-10-13
 */
public interface TagArticleMapper extends BaseMapper<TagArticle> {
    boolean insertTagArticle(String tagTitle, Integer articleId);
}
