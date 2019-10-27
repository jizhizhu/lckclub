package com.lckclub.config.service;

import com.lckclub.model.SensitiveWord;
import com.lckclub.service.ISensitiveWordService;
import com.lckclub.util.SensitiveWordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Component
@DependsOn("mybatisPlusConfig")
public class SensitiveWordFilterService {

  @Autowired
  private ISensitiveWordService sensitiveWordService;

  // 初始化过滤器
  @PostConstruct
  public void init() {
    List<SensitiveWord> sensitiveWords = sensitiveWordService.selectAll();
    Set<String> sensitiveWordSet = new HashSet<>();
    for (SensitiveWord sensitiveWord : sensitiveWords) {
      sensitiveWordSet.add(sensitiveWord.getWord());
    }
    SensitiveWordUtil.init(sensitiveWordSet);
  }
}
