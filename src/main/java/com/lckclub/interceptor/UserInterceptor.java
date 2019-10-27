package com.lckclub.interceptor;

import com.lckclub.model.User;
import com.lckclub.service.ISystemConfigService;
import com.lckclub.service.impl.UserService;
import com.lckclub.util.CookieUtil;
import com.lckclub.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Component
public class UserInterceptor implements HandlerInterceptor {

  @Autowired
  private CookieUtil cookieUtil;
  @Autowired
  private ISystemConfigService systemConfigService;
  @Autowired
  private UserService userService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    HttpSession session = request.getSession();
    User user = (User) session.getAttribute("_user");
    if (user == null) {
      String token = cookieUtil.getCookie(systemConfigService.selectAllConfig().get("cookie_name").toString());
      if (!StringUtils.isEmpty(token)) {
        user = userService.selectByToken(token);
        session.setAttribute("_user", user);
      }
    }
    if (user == null) {
      HttpUtil.responseWrite(request, response);
      return false;
    } else {
      return true;
    }
  }
}
