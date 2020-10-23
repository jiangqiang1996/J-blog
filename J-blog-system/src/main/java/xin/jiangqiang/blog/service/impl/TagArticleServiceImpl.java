package xin.jiangqiang.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import xin.jiangqiang.blog.entities.Tag;
import xin.jiangqiang.blog.entities.TagArticle;
import xin.jiangqiang.blog.mapper.TagArticleMapper;
import xin.jiangqiang.blog.service.ITagArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xin.jiangqiang.blog.service.ITagService;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 标签-文章关联表 服务实现类
 * </p>
 *
 * @author jiangqiang--blog.jiangqiang.xin
 * @since 2020-10-13
 */
@Service
public class TagArticleServiceImpl extends ServiceImpl<TagArticleMapper, TagArticle> implements ITagArticleService {
    @Autowired
    ITagService tagService;

    @Override
    public boolean saveOrUpdateList(List<TagArticle> entityList) {
        for (TagArticle tagArticle : entityList) {
            QueryWrapper<TagArticle> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("articleId", tagArticle.getArticleId());
            queryWrapper.eq("tagId", tagArticle.getTagId());
            TagArticle tmpTagArtcile = getOne(queryWrapper);
            if (tmpTagArtcile == null) {
                save(tagArticle);
            }
        }
        return true;
    }
    @Override
    public boolean saveOrUpdateString(String tagString, Integer articleId) {
        List<TagArticle> tagArticles = new ArrayList<>();
        if (StringUtils.isNotBlank(tagString)) {
            String[] tagTitles = tagString.split(",");
            for (String tagTitle : tagTitles) {
                if (StringUtils.isNotBlank(tagTitle)) {
                    Tag tmpTag = tagService.queryOneByColumn("tagTitle", tagTitle);
                    TagArticle tagArticle = new TagArticle();
                    tagArticle.setTagId(tmpTag.getId());
                    tagArticle.setArticleId(articleId);
                    tagArticles.add(tagArticle);
                }
            }
        }
        return saveOrUpdateBatch(tagArticles);
    }
}
