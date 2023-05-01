package com.ateh.eh.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.ateh.eh.entity.User;
import com.ateh.eh.mapper.UserMapper;
import com.ateh.eh.utils.OSSUtil;
import com.ateh.eh.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/upload")
    @ApiOperation("上传文件")
    public Result upload(MultipartFile file, String name, String prefix, Long userId) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(file.getBytes());
            int index = name.lastIndexOf(".");
            String suffix = index > -1 ? name.substring(index) : ".png";
            String filePath = OSSUtil.uploadFile(prefix, inputStream, suffix);
            if (StrUtil.isNotEmpty(filePath)) {
                if ("avatar/".equals(prefix)) {
                    User user = new User();
                    user.setAvatar(filePath);
                    user.setUserId(userId);
                    userMapper.updateById(user);
                }
                return Result.success(filePath, "成功上传!");
            }
        } catch (IOException e) {
            log.error("文件上传错误", e);
        }
        return Result.error("上传失败!");
    }

    @PostMapping("/uploadTest")
    @ApiOperation("上传文件测试")
    public Result uploadTest(MultipartFile file) throws IOException {
        List<String> nameList = new ArrayList<>();
        String originalFilename = file.getOriginalFilename();
        InputStream is = file.getInputStream();
        String name = RandomUtil.randomString(16) + originalFilename.substring(originalFilename.lastIndexOf("."));

        FileOutputStream os = new FileOutputStream("D:/FFOutput/" + name);
        FileCopyUtils.copy(is, os);
        nameList.add("http://localhost:8888/static/" + name);

        is.close();
        os.close();
        return Result.success(nameList);
    }

    @GetMapping("/getFile/{fileName}")
    @ApiOperation("获取文件")
    public ResponseEntity<byte[]> getFile(@PathVariable("fileName")String fileName) throws IOException {
        return null;
    }
}