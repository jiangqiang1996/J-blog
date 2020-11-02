package xin.jiangqiang.blog.admin.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xin.jiangqiang.blog.util.base.Result;
import xin.jiangqiang.blog.util.config.JblogProperties;
import xin.jiangqiang.blog.util.tools.DateUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JiangQiang
 * @date 2020/10/10 14:14
 */
@RestController
@RequestMapping("/admin/file")
public class FileUploadController {
    Logger logger = LoggerFactory.getLogger(FileUploadController.class);
    @Autowired
    JblogProperties jblogProperties;

    /**
     * 服务器上存储的文件名
     *
     * @param originalFilename 原始文件名
     * @param length           随机数长度
     * @return
     */
    public String getServerFileName(String originalFilename, int length) {
        String suffix = "";
        String[] tmpStr = originalFilename.split("\\.");
        if (tmpStr.length > 1) {
            suffix = "." + tmpStr[tmpStr.length - 1];
        }
        return DateUtil.getCurrentTime(length) + suffix;
    }

    /**
     * 创建文件路径,可以放上传文件的拦截器里面
     */
    public void init() {
        File fileDir = new File(jblogProperties.getFileResources().getPath());
        File fileCacheDir = new File(jblogProperties.getFileResources().getCachePath());
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        if (!fileCacheDir.exists()) {
            fileCacheDir.mkdirs();
        }
    }

    @ApiImplicitParam(name = "file[]", value = "文件对象数组", required = true)
    @ApiOperation("上传文件接口")
    //@RequestParam("fileMd5") String fileMd5
    @PostMapping("/uploadFile")
    public Result uploadSingle(@RequestParam("file[]") MultipartFile[] multipartFiles, HttpServletRequest request) {
        List<String> errFiles = new ArrayList<>();
        Map<String, String> succMap = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        init();
        for (MultipartFile multipartFile : multipartFiles) {
            String fileName = multipartFile.getOriginalFilename();//原始文件名
            String serverFileName = getServerFileName(fileName, 3);
            File file = new File(jblogProperties.getFileResources().getPath() + serverFileName);
            try {
                multipartFile.transferTo(file);
                //文件名，文件的http访问路径
                succMap.put(fileName, request.getContextPath() + jblogProperties.getFileResources().getPathPattern().replace("**", "") + serverFileName);
            } catch (IOException e) {
                logger.info(fileName + "上传失败");
                logger.info(e.getMessage());
                errFiles.add(fileName);
            }
        }
        data.put("errFiles", errFiles);
        data.put("succMap", succMap);
        if (CollectionUtils.isEmpty(errFiles)) {
            return Result.ok("文件上传成功", data);
        }else{
            if (MapUtils.isEmpty(succMap)) {
                return Result.error("文件上传失败", data);
            } else {
                return Result.error("部分文件上传失败，请检查上传日志信息", data);
            }
        }
    }

}
