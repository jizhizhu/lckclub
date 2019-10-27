package com.lckclub.mapper;

import com.lckclub.model.Comment;
import com.lckclub.model.vo.CommentsByTopic;
import com.lckclub.util.MyPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public interface CommentMapper extends BaseMapper<Comment> {

  List<CommentsByTopic> selectByTopicId(@Param("topicId") Integer topicId);

  MyPage<Map<String, Object>> selectByUserId(MyPage<Map<String, Object>> iPage, @Param("userId") Integer userId);

  MyPage<Map<String, Object>> selectAllForAdmin(MyPage<Map<String, Object>> iPage, @Param("startDate") String
      startDate, @Param("endDate") String endDate, @Param("username") String username);

  int countToday();
}
