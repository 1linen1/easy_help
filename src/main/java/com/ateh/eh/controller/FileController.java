package com.ateh.eh.controller;

import cn.hutool.core.util.StrUtil;
import com.ateh.eh.utils.OSSUtil;
import com.ateh.eh.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: FileController.java
 *
 * @author huang.yijie
 * 时间: 2023/4/2 16:21
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@RestController
@RequestMapping("/api/file")
@Slf4j
@Api("文件上传接口")
public class FileController {

    @PostMapping("/upload")
    @ApiOperation("上传文件")
    public Result upload(MultipartFile file, String name, String prefix) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(file.getBytes());
            int index = name.lastIndexOf(".");
            String suffix = index > -1 ? name.substring(index) : ".png";
            String filePath = OSSUtil.uploadFile(prefix, inputStream, suffix);
            if (StrUtil.isNotEmpty(filePath)) {
                return Result.success(filePath, "成功上传!");
            }
        } catch (IOException e) {
            log.error("文件上传错误", e);
        }
        return Result.error("上传失败!");
    }
}
