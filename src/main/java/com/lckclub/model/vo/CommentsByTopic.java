package com.lckclub.model.vo;

import com.lckclub.model.Comment;

import java.io.Serializable;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public class CommentsByTopic extends Comment implements Serializable {
  private static final long serialVersionUID = 8082073760910701836L;
  // 话题下面的评论列表单个对象的数据结构

  private String username;
  private String avatar;
  // 评论的层级，直接评论话题的，layer即为0，如果回复了评论的，则当前回复的layer为评论对象的layer+1
  private Integer layer;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public Integer getLayer() {
    return layer;
  }

  public void setLayer(Integer layer) {
    this.layer = layer;
  }
}
