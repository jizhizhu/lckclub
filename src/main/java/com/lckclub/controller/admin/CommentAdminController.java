package com.lckclub.controller.admin;

import com.lckclub.model.Comment;
import com.lckclub.model.Topic;
import com.lckclub.service.ICommentService;
import com.lckclub.service.ITopicService;
import com.lckclub.util.MyPage;
import com.lckclub.util.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Controller
@RequestMapping("/admin/comment")
public class CommentAdminController extends BaseAdminController {

  @Autowired
  private ICommentService commentService;
  @Autowired
  private ITopicService topicService;

  @RequiresPermissions("comment:list")
  @GetMapping("/list")
  public String list(@RequestParam(defaultValue = "1") Integer pageNo, String startDate, String endDate, String
      username, Model model) {
    if (StringUtils.isEmpty(startDate)) startDate = null;
    if (StringUtils.isEmpty(endDate)) endDate = null;
    if (StringUtils.isEmpty(username)) username = null;
    MyPage<Map<String, Object>> page = commentService.selectAllForAdmin(pageNo, startDate, endDate, username);
    model.addAttribute("page", page);
    model.addAttribute("startDate", startDate);
    model.addAttribute("endDate", endDate);
    model.addAttribute("username", username);
    return "admin/comment/list";
  }

  @RequiresPermissions("comment:edit")
  @GetMapping("/edit")
  public String edit(Integer id, Model model) {
    Comment comment = commentService.selectById(id);
    Topic topic = topicService.selectById(comment.getTopicId());
    model.addAttribute("comment", comment);
    model.addAttribute("topic", topic);
    return "admin/comment/edit";
  }

  @RequiresPermissions("comment:edit")
  @PostMapping("/edit")
  @ResponseBody
  public Result update(Integer id, String content) {
    Comment comment = commentService.selectById(id);
    comment.setContent(content);
    commentService.update(comment);
    return success();
  }

  @RequiresPermissions("comment:delete")
  @GetMapping("/delete")
  @ResponseBody
  public Result delete(Integer id) {
    Comment comment = commentService.selectById(id);
    commentService.delete(comment, null);
    return success();
  }
}
