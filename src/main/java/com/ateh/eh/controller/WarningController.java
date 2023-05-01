package com.ateh.eh.controller;

import com.ateh.eh.entity.Warning;
import com.ateh.eh.service.IWarningService;
import com.ateh.eh.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: WarningController.java
 *
 * @author huang.yijie
 * 时间: 2023/5/1 21:08
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@RestController
@RequestMapping("/api/warning")
@Api("举报接口")
public class WarningController {

    @Autowired
    private IWarningService warningService;

    @PostMapping("/addWarning")
    @ApiOperation("添加举报")
    public Result addWarning(@RequestBody Warning warning) {
        return warningService.addWarning(warning);
    }


    @PostMapping("/handleWarning")
    @ApiOperation("处理举报")
    public Result handleWarning(@RequestBody Warning warning) {
        return warningService.handleWarning(warning);
    }

}
