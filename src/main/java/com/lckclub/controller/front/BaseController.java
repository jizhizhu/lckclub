package com.lckclub.controller.front;

import com.lckclub.model.User;
import com.lckclub.service.ISystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public class BaseController {

  @Autowired
  private ISystemConfigService systemConfigService;

  protected String redirect(String path) {
    return "redirect:" + path;
  }

  protected User getUser() {
    HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder
        .getRequestAttributes())).getRequest();
    HttpSession session = request.getSession();
    return (User) session.getAttribute("_user");
  }

  // 只针对前台页面的模板路径渲染，后台不变
  protected String render(String path) {
    return String.format("theme/%s/%s", systemConfigService.selectAllConfig().get("theme").toString(), path);
  }

}
