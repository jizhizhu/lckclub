package com.lckclub.mapper;

import com.lckclub.model.AdminUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public interface AdminUserMapper extends BaseMapper<AdminUser> {

  List<Map<String, Object>> selectAll();
}
