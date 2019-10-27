package com.lckclub.hook;

import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public class TopicServiceHook {

  @Pointcut("execution(public * com.lckclub.service.ITopicService.search(..))")
  public void search() {
  }

  @Pointcut("execution(public * com.lckclub.service.ITopicService.selectAuthorOtherTopic(..))")
  public void selectAuthorOtherTopic() {
  }

  @Pointcut("execution(public * com.lckclub.service.ITopicService.selectByUserId(..))")
  public void selectByUserId() {
  }

  @Pointcut("execution(public * com.lckclub.service.ITopicService.insert(..))")
  public void insert() {
  }

  @Pointcut("execution(public * com.lckclub.service.ITopicService.selectById(..))")
  public void selectById() {
  }

  @Pointcut("execution(public * com.lckclub.service.ITopicService.selectByTitle(..))")
  public void selectByTitle() {
  }

  @Pointcut("execution(public * com.lckclub.service.ITopicService.update(..))")
  public void update() {
  }

  @Pointcut("execution(public * com.lckclub.service.ITopicService.delete(..))")
  public void delete() {
  }

  @Pointcut("execution(public * com.lckclub.service.ITopicService.deleteByUserId(..))")
  public void deleteByUserId() {
  }

  @Pointcut("execution(public * com.lckclub.service.ITopicService.vote(..))")
  public void vote() {
  }

  @Pointcut("execution(public * com.lckclub.service.ITopicService.updateViewCount(..))")
  public void updateViewCount() {
  }

}
