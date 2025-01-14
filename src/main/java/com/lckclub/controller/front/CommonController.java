package com.lckclub.controller.front;

import com.lckclub.controller.api.BaseApiController;
import com.lckclub.util.captcha.Captcha;
import com.lckclub.util.captcha.GifCaptcha;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Controller
@RequestMapping("/common")
public class CommonController extends BaseApiController {

  // gif 验证码
  @GetMapping("/captcha")
  public void captcha(HttpServletResponse response, HttpSession session) throws IOException {
    Captcha captcha = new GifCaptcha();
    captcha.out(response.getOutputStream());
    String text = captcha.text();
    session.setAttribute("_captcha", text);
  }

}
