package xin.jiangqiang.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang.StringUtils;
import xin.jiangqiang.blog.entities.Tag;
import xin.jiangqiang.blog.mapper.TagMapper;
import xin.jiangqiang.blog.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author jiangqiang--blog.jiangqiang.xin
 * @since 2020-10-13
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

    @Override
    public boolean saveOrUpdateList(List<Tag> entityList) {
        for (Tag tag : entityList) {
            Tag tmpTag = queryOneByColumn("tagTitle", tag.getTagTitle());
            if (tmpTag == null) {
                save(tag);
            }
        }
        return true;
    }
    @Override
    public boolean saveOrUpdateTagString(String tagString) {
        List<Tag> tags = new ArrayList<>();
        if (StringUtils.isNotBlank(tagString)) {
            String[] tagTitles = tagString.split(",");
            for (String tagTitle : tagTitles) {
                if (StringUtils.isNotBlank(tagTitle)) {
                    tags.add(new Tag().setTagTitle(tagTitle.trim()));
                }
            }
        }
        return saveOrUpdateList(tags);
    }

    @Override
    public Tag queryOneByColumn(String column, String value) {
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(column, value);
        return getOne(queryWrapper);
    }

}
