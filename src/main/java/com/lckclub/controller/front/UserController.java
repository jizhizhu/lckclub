package com.lckclub.controller.front;

import com.lckclub.model.OAuthUser;
import com.lckclub.model.User;
import com.lckclub.service.ICollectService;
import com.lckclub.service.IOAuthUserService;
import com.lckclub.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

  @Autowired
  private IUserService userService;
  @Autowired
  private ICollectService collectService;
  @Autowired
  private IOAuthUserService oAuthUserService;

  @GetMapping("/{username}")
  public String profile(@PathVariable String username, Model model) {
    // 查询用户个人信息
    User user = userService.selectByUsername(username);
    // 查询oauth登录的用户信息
    List<OAuthUser> oAuthUsers = oAuthUserService.selectByUserId(user.getId());
    // 查询用户收藏的话题数
    Integer collectCount = collectService.countByUserId(user.getId());

    // 找出oauth登录里有没有github，有的话把github的login提取出来
    List<String> logins = oAuthUsers.stream().filter(oAuthUser -> oAuthUser.getType().equals("GITHUB")).map
        (OAuthUser::getLogin).collect(Collectors.toList());
    if (logins.size() > 0) {
      model.addAttribute("githubLogin", logins.get(0));
    }

    model.addAttribute("user", user);
    model.addAttribute("username", username);
    model.addAttribute("oAuthUsers", oAuthUsers);
    model.addAttribute("collectCount", collectCount);
    return render("user/profile");
  }

  @GetMapping("/{username}/topics")
  public String topics(@PathVariable String username, @RequestParam(defaultValue = "1") Integer pageNo, Model model) {
    model.addAttribute("username", username);
    model.addAttribute("pageNo", pageNo);
    return render("user/topics");
  }

  @GetMapping("/{username}/comments")
  public String comments(@PathVariable String username, @RequestParam(defaultValue = "1") Integer pageNo, Model model) {
    model.addAttribute("username", username);
    model.addAttribute("pageNo", pageNo);
    return render("user/comments");
  }

  @GetMapping("/{username}/collects")
  public String collects(@PathVariable String username, @RequestParam(defaultValue = "1") Integer pageNo, Model model) {
    model.addAttribute("username", username);
    model.addAttribute("pageNo", pageNo);
    return render("user/collects");
  }
}
