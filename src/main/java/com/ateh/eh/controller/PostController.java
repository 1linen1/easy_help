package com.ateh.eh.controller;

import com.ateh.eh.common.CommonConstants;
import com.ateh.eh.entity.Post;
import com.ateh.eh.entity.ext.PostExt;
import com.ateh.eh.entity.ext.UserExt;
import com.ateh.eh.mapper.FollowsMapper;
import com.ateh.eh.mapper.PostMapper;
import com.ateh.eh.req.follows.FollowsPageReq;
import com.ateh.eh.req.posts.PostPageReq;
import com.ateh.eh.service.IFollowsService;
import com.ateh.eh.service.IPostService;
import com.ateh.eh.utils.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: FileController.java
 *
 * @author huang.yijie
 * 时间: 2023/3/30 22:30
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@RestController
@RequestMapping("/api/post")
@Api("帖子接口")
public class PostController {

    @Autowired
    private IPostService postService;

    @Autowired
    private PostMapper postMapper;


    @PostMapping("/addPost")
    @ApiOperation("新增帖子")
    public Result addPost(@RequestBody Post post) {
        return postService.addPost(post);
    }

    @GetMapping("/getPostById/{postId}")
    @ApiOperation("查询帖子信息")
    public Result getPostById(@PathVariable("postId") Long postId) {
        return Result.success(postMapper.selectById(postId));
    }

    @PostMapping("/qryPostPage")
    @ApiOperation("分页查询帖子")
    public Result<IPage<PostExt>> qryPostPage(@RequestBody PostPageReq req) {
        return postService.qryPostPage(req);
    }

    @GetMapping("/addViews/{postId}")
    @ApiOperation("增加浏览量")
    public Result addPostViews(@PathVariable("postId") Long postId) {
        return postService.addPostViews(postId);
    }


    @GetMapping("/resolvePost/{postId}")
    @ApiOperation("帖子状态更新为已解决")
    public Result resolvePost(@PathVariable("postId") Long postId) {
        Post post = new Post();
        post.setPostId(postId);
        post.setResolved(CommonConstants.POST_RESOLVED);
        return Result.success(postService.updateById(post));
    }

    @GetMapping("/qryValidScores/{postId}")
    @ApiOperation("查询帖子有效积分")
    public Result qryValidScores(@PathVariable("postId") Long postId) {
        return Result.success(postService.qryValidScores(postId));
    }

    @PostMapping("/qryHelpPostPage")
    @ApiOperation("查询帮助过的帖子")
    public Result qryHelpPostPage(@RequestBody PostPageReq req) {
        return postService.qryHelpPostPage(req);
    }

    @PostMapping("/qryPostPageByContent")
    @ApiOperation("根据帖子内容/用户名称分页查询帖子")
    public Result<IPage<PostExt>> qryPostPageByContent(@RequestBody PostPageReq req) {
        return postService.qryPostPageByContent(req);
    }

    @PostMapping("/qryDynamicPage")
    @ApiOperation("查询用户动态")
    public Result<IPage<PostExt>> qryDynamicPage(@RequestBody PostPageReq req) {
        return postService.qryDynamicPage(req);
    }


}
