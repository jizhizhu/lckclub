package com.lckclub.service;

import com.lckclub.model.Tag;
import com.lckclub.model.TopicTag;

import java.util.List;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public interface ITopicTagService {
  List<TopicTag> selectByTopicId(Integer topicId);

  List<TopicTag> selectByTagId(Integer tagId);

  void insertTopicTag(Integer topicId, List<Tag> tagList);

  // 删除话题所有关联的标签记录
  void deleteByTopicId(Integer id);
}
