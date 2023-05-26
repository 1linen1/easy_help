package com.ateh.eh.controller;

import com.ateh.eh.entity.Warning;
import com.ateh.eh.req.warning.HandleAppealReq;
import com.ateh.eh.req.warning.HandleWarningReq;
import com.ateh.eh.req.warning.WarningPageReq;
import com.ateh.eh.service.IWarningService;
import com.ateh.eh.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
    public Result handleWarning(@RequestBody HandleWarningReq req) {
        return warningService.handleWarning(req);
    }

    @PostMapping("/updateWarning")
    @ApiOperation("更新举报状态")
    public Result updateWarning(@RequestBody Warning warning) {
        return warningService.updateWarning(warning);
    }

    @PostMapping("/qryAllWarning")
    @ApiOperation("查询所有举报")
    public Result qryAllWarning(@RequestBody WarningPageReq req) {
        return warningService.qryAllWarning(req);
    }

    @PostMapping("/handleAppeal")
    @ApiOperation("处理申诉")
    public Result handleAppeal(@RequestBody HandleAppealReq req) {
        return warningService.handleAppeal(req);
    }

    @PostMapping("/qryMyWarning")
    @ApiOperation("查询举报结果")
    public Result qryMyWarning(@RequestBody WarningPageReq req) {
        return warningService.qryMyWarning(req);
    }

}
