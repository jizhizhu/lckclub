package com.lckclub.controller.front;

import com.lckclub.model.Comment;
import com.lckclub.model.Topic;
import com.lckclub.service.ICommentService;
import com.lckclub.service.ITopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Controller
@RequestMapping("/comment")
public class CommentController extends BaseController {

  @Autowired
  private ICommentService commentService;
  @Autowired
  private ITopicService topicService;

  // 编辑评论
  @GetMapping("/edit/{id}")
  public String edit(@PathVariable Integer id, Model model) {
    Comment comment = commentService.selectById(id);
    Topic topic = topicService.selectById(comment.getTopicId());
    model.addAttribute("comment", comment);
    model.addAttribute("topic", topic);
    return render("comment/edit");
  }
}
