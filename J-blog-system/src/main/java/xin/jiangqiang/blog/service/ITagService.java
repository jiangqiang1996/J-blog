package xin.jiangqiang.blog.service;

import org.springframework.transaction.annotation.Transactional;
import xin.jiangqiang.blog.entities.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 标签表 服务类
 * </p>
 *
 * @author jiangqiang--blog.jiangqiang.xin
 * @since 2020-10-13
 */
public interface ITagService extends IService<Tag> {
    /**
     * 只会新增不存在的记录
     * @param entityList
     * @return
     */
    @Transactional
    boolean saveOrUpdateList(List<Tag> entityList);

    @Transactional
    boolean saveOrUpdateTagString(String tagString);

    Tag queryOneByColumn(String column, String value);
}
