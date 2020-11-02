package xin.jiangqiang.blog.util.base;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xin.jiangqiang.blog.util.enums.ResultEnum;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JiangQiang
 * 用于封装接口统一响应结果
 * @date 2020/10/1 12:32
 */
@Data
@NoArgsConstructor // 无参构造方法
@AllArgsConstructor // 有参构造方法
@Api("返回结果封装对象--所有返回json的请求,响应格式都是如此")
public final class Result implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(Result.class);

    private static final long serialVersionUID = 1L;

    /**
     * 响应业务状态码
     */
    @ApiModelProperty(value = "响应码,200表示成功,其余都表示操作失败", required = true)
    private Integer code;

    /**
     * 响应信息
     */
    @ApiModelProperty(value = "响应信息", required = true)
    private String message;

    /**
     * 响应中的数据
     */
    @ApiModelProperty(value = "响应数据,查询到的结果", required = false)
    private Object data;

    public static Result ok() {
        return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getDesc(), null);
    }

    public static Result ok(Object data) {
        return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getDesc(), data);
    }

    public static Result ok(String message, Object data) {
        return new Result(ResultEnum.SUCCESS.getCode(), message, data);
    }

    public static Result error(String message) {
        logger.debug("返回错误：code={}, message={}", ResultEnum.ERROR.getCode(), message);
        return new Result(ResultEnum.ERROR.getCode(), message, null);
    }

    public static Result error(String message, Object data) {
        logger.debug("返回错误：code={}, message={}, data={}", ResultEnum.ERROR.getCode(), message, data);
        return new Result(ResultEnum.ERROR.getCode(), message, data);
    }

    public static Result build(int code, String message) {
        logger.debug("返回结果：code={}, message={}", code, message);
        return new Result(code, message, null);
    }

    public static Result build(ResultEnum resultEnum) {
        logger.debug("返回结果：code={}, message={}", resultEnum.getCode(), resultEnum.getDesc());
        return new Result(resultEnum.getCode(), resultEnum.getDesc(), null);
    }

    public static Result build(int code, String message, Object data) {
        logger.debug("返回结果：code={}, message={}, data={}", code, message, data);
        return new Result(code, message, data);
    }

    /**
     * 如果返回一个list，则使用此方法
     *
     * @param data
     * @return
     */
    public static Result list(Object data) {
        Map<String, Object> map = new HashMap<>();
        map.put("records", data);
        return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getDesc(), map);
    }

    public String toJsonString() {
        return JSON.toJSONString(this);
    }

}