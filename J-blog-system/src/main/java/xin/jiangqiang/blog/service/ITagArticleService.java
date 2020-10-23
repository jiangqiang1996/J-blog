package xin.jiangqiang.blog.service;

import org.springframework.transaction.annotation.Transactional;
import xin.jiangqiang.blog.entities.TagArticle;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 标签-文章关联表 服务类
 * </p>
 *
 * @author jiangqiang--blog.jiangqiang.xin
 * @since 2020-10-13
 */
public interface ITagArticleService extends IService<TagArticle> {
    @Transactional
    boolean saveOrUpdateList(List<TagArticle> entityList);

    @Transactional
    boolean saveOrUpdateString(String tagString, Integer articleId);
}
