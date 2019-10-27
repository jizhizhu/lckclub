package com.lckclub.controller.admin;

import com.lckclub.controller.api.BaseApiController;
import com.lckclub.model.AdminUser;
import com.lckclub.service.IAdminUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public class BaseAdminController extends BaseApiController {

  @Autowired
  private IAdminUserService adminUserService;

  // 可以将传递到controller里的参数中Date类型的从String转成Date类型的对象
  // 这货没有想象中的好用，还不如我手动控制String转Date了
  //  @InitBinder
  //  public void initBinder(ServletRequestDataBinder binder) {
  //    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
  //    binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
  //  }

  protected AdminUser getAdminUser() {
    Subject subject = SecurityUtils.getSubject();
    String principal = (String) subject.getPrincipal();
    return adminUserService.selectByUsername(principal);
  }

}
