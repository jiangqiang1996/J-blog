package xin.jiangqiang.blog.util.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author JiangQiang
 * 请求参数基础类
 * @date 2020/10/1 12:00
 */
@Accessors(chain = true)
@Data
public class BaseRequest<T> implements Serializable {

    @ApiModelProperty(value = "页码，页码和每页条数必须同时传入或同时不传", required = true)
    private Long current;

    @ApiModelProperty(value = "每页显示多少条,页码和每页条数必须同时传入或同时不传", required = true)
    private Long size;

    /**
     * 封装分页对象，如果分页条件为空，则分页对象为空
     *
     * @return
     */
    @ApiModelProperty(hidden = true) // 不在swagger接口文档中显示
    public IPage<T> getPage() {
        if (this.getCurrent() != null && this.getSize() != null) {
            return new Page<T>().setCurrent(this.current).setSize(this.size);
        } else {
            return null;
        }
    }

    /**
     * 返回null 表示分页条件要么为空，要么合法。
     *
     * @param request
     * @return
     */
    @ApiModelProperty(hidden = true) // 不在swagger接口文档中显示
    public static Result checkFields(BaseRequest request) {
        if (request != null) {
            if (request.getCurrent() == null) {
                if (request.getSize() != null) {
                    return Result.error("分页参数不全");
                }
            } else {
                if (request.getSize() == null) {
                    return Result.error("分页参数不全");
                } else {
                    if (request.getCurrent() <= 0 || request.getSize() <= 0) {
                        return Result.error("分页参数错误");
                    }
                }
            }
        }
        return null;
    }
}
