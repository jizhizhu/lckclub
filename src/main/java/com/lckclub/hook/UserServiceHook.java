package com.lckclub.hook;

import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public class UserServiceHook {

  @Pointcut("execution(public * com.lckclub.service.IUserService.selectByUsername(..))")
  public void selectByUsername() {
  }

  @Pointcut("execution(public * com.lckclub.service.IUserService.addUser(..))")
  public void addUser() {
  }

  @Pointcut("execution(public * com.lckclub.service.IUserService.addUserWithMobile(..))")
  public void addUserWithMobile() {
  }

  @Pointcut("execution(public * com.lckclub.service.IUserService.selectByToken(..))")
  public void selectByToken() {
  }

  @Pointcut("execution(public * com.lckclub.service.IUserService.selectByMobile(..))")
  public void selectByMobile() {
  }

  @Pointcut("execution(public * com.lckclub.service.IUserService.selectByEmail(..))")
  public void selectByEmail() {
  }

  @Pointcut("execution(public * com.lckclub.service.IUserService.selectById(..))")
  public void selectById() {
  }

  @Pointcut("execution(public * com.lckclub.service.IUserService.selectTop(..))")
  public void selectTop() {
  }

  @Pointcut("execution(public * com.lckclub.service.IUserService.update(..))")
  public void update() {
  }

  @Pointcut("execution(public * com.lckclub.service.IUserService.delRedisUser(..))")
  public void delRedisUser() {
  }

}
