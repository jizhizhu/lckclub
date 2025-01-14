package com.lckclub.mapper;

import com.lckclub.model.Tag;
import com.lckclub.util.MyPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public interface TagMapper extends BaseMapper<Tag> {

  MyPage<Map<String, Object>> selectTopicByTagId(MyPage<Map<String, Object>> iPage, @Param("tagId") Integer tagId);

  int countToday();
}
