package com.lckclub.controller.api;

import com.lckclub.exception.ApiAssert;
import com.lckclub.model.Collect;
import com.lckclub.model.User;
import com.lckclub.service.ICollectService;
import com.lckclub.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@RestController
@RequestMapping("/api/collect")
public class CollectApiController extends BaseApiController {

  @Autowired
  private ICollectService collectService;

  // 收藏话题
  @PostMapping("/{topicId}")
  public Result get(@PathVariable Integer topicId) {
    User user = getApiUser();
    Collect collect = collectService.selectByTopicIdAndUserId(topicId, user.getId());
    ApiAssert.isNull(collect, "做人要知足，每人每个话题只能收藏一次哦！");
    collectService.insert(topicId, user);
    return success();
  }

  // 取消收藏
  @DeleteMapping("/{topicId}")
  public Result delete(@PathVariable Integer topicId) {
    User user = getApiUser();
    Collect collect = collectService.selectByTopicIdAndUserId(topicId, user.getId());
    ApiAssert.notNull(collect, "你都没有收藏这个话题，哪来的取消？");
    collectService.delete(topicId, user.getId());
    return success();
  }
}
