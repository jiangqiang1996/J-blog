package xin.jiangqiang.blog.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;

/**
 * @author JiangQiang
 * @date 2020/10/15 9:28
 */
//@RestControllerAdvice
@Component
public class GlobalExceptionHandler extends DefaultErrorAttributes {
    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 修改异常默认json响应格式
     *
     * @param webRequest
     * @param includeStackTrace
     * @return
     */
//    @Override
//    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
//        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
//        Map<String, Object> result = new HashMap<>();
//        result.put("code", errorAttributes.get("status"));
//        result.put("message", errorAttributes.get("error"));
//        result.put("data", errorAttributes.get("message"));
//        logger.info(result.toString());
//        return result;
//    }

//    @ExceptionHandler(value = Exception.class)
//    public Result defaultErrorHandler(Exception e) {
//        Result result = Result.error(e.getMessage());
//        logger.info(result.toJsonString());
//        return result;
//    }
}