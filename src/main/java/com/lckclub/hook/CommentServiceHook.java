package com.lckclub.hook;

import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public class CommentServiceHook {

  @Pointcut("execution(public * com.lckclub.service.ICommentService.selectByTopicId(..))")
  public void selectByTopicId() {
  }

  @Pointcut("execution(public * com.lckclub.service.ICommentService.deleteByTopicId(..))")
  public void deleteByTopicId() {
  }

  @Pointcut("execution(public * com.lckclub.service.ICommentService.deleteByUserId(..))")
  public void deleteByUserId() {
  }

  @Pointcut("execution(public * com.lckclub.service.ICommentService.insert(..))")
  public void insert() {
  }

  @Pointcut("execution(public * com.lckclub.service.ICommentService.selectById(..))")
  public void selectById() {
  }

  @Pointcut("execution(public * com.lckclub.service.ICommentService.update(..))")
  public void update() {
  }

  @Pointcut("execution(public * com.lckclub.service.ICommentService.vote(..))")
  public void vote() {
  }

  @Pointcut("execution(public * com.lckclub.service.ICommentService.delete(..))")
  public void delete() {
  }

  @Pointcut("execution(public * com.lckclub.service.ICommentService.selectByUserId(..))")
  public void selectByUserId() {
  }

}
