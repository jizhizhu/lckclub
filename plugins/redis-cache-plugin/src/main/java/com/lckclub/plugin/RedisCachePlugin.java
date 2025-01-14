package com.lckclub.plugin;

import com.lckclub.model.Comment;
import com.lckclub.model.Topic;
import com.lckclub.model.User;
import com.lckclub.model.vo.CommentsByTopic;
import com.lckclub.service.ISystemConfigService;
import com.lckclub.service.ITopicService;
import com.lckclub.util.JsonUtil;
import com.alibaba.fastjson.TypeReference;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Component
@Aspect
public class RedisCachePlugin {

  @Autowired
  private RedisService redisService;
  @Autowired
  private ITopicService topicService;
  @Autowired
  private ISystemConfigService systemConfigService;

  // ---------- topic cache start ----------

  @Around("com.lckclub.hook.TopicServiceHook.selectById()")
  public Object topicSelectById(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    String topicJson = redisService.getString(String.format(RedisKeys.REDIS_TOPIC_KEY, proceedingJoinPoint.getArgs()
        [0]));
    if (topicJson == null) {
      Object topic = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
      // 缓存在redis里
      redisService.setString(String.format(RedisKeys.REDIS_TOPIC_KEY, proceedingJoinPoint.getArgs()[0]), JsonUtil
          .objectToJson(topic));
      return topic;
    } else {
      return JsonUtil.jsonToObject(topicJson, Topic.class);
    }
  }

  @Around("com.lckclub.hook.TopicServiceHook.updateViewCount()")
  public Object topicUpdateViewCount(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    Topic topic = (Topic) proceedingJoinPoint.getArgs()[0];
    String ip = (String) proceedingJoinPoint.getArgs()[1];
    String s = redisService.getString(String.format(RedisKeys.REDIS_TOPIC_VIEW_IP_ID_KEY, ip, topic.getId()));
    if (redisService.isRedisConfig()) {
      if (s == null) {
        topic.setView(topic.getView() + 1);
        topicService.update(topic, null);
        redisService.setString(String.format(RedisKeys.REDIS_TOPIC_VIEW_IP_ID_KEY, ip, topic.getId()), String.valueOf
            (topic.getId()), Integer.parseInt(systemConfigService.selectAllConfig().get
            ("topic_view_increase_interval").toString()));
      }
      return topic;
    } else {
      return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
    }
  }

  @After("com.lckclub.hook.TopicServiceHook.update()")
  public void topicUpdate(JoinPoint joinPoint) {
    Topic topic = (Topic) joinPoint.getArgs()[0];
    // 缓存到redis里
    redisService.setString(String.format(RedisKeys.REDIS_TOPIC_KEY, topic.getId()), JsonUtil.objectToJson(topic));
  }

  // ---------- topic cache end ----------

  // ---------- comment cache start ----------

  @Around("com.lckclub.hook.CommentServiceHook.selectByTopicId()")
  public Object commentSelectByTopicId(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    Integer topicId = (Integer) proceedingJoinPoint.getArgs()[0];
    String commentsJson = redisService.getString(String.format(RedisKeys.REDIS_COMMENTS_KEY, topicId));
    if (commentsJson != null) {
      // 带泛型转换, 这里如果不带泛型转换，会报错
      return JsonUtil.jsonToObject(commentsJson, new TypeReference<List<CommentsByTopic>>() {
      });
    } else {
      Object returnValue = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
      redisService.setString(String.format(RedisKeys.REDIS_COMMENTS_KEY, topicId), JsonUtil.objectToJson(returnValue));
      return returnValue;
    }
  }

  @After("com.lckclub.hook.CommentServiceHook.insert()")
  public void commentInsert(JoinPoint joinPoint) {
    Comment comment = (Comment) joinPoint.getArgs()[0];
    redisService.delString(String.format(RedisKeys.REDIS_COMMENTS_KEY, comment.getTopicId()));
  }

  @After("com.lckclub.hook.CommentServiceHook.update()")
  public void commentUpdate(JoinPoint joinPoint) {
    Comment comment = (Comment) joinPoint.getArgs()[0];
    redisService.delString(String.format(RedisKeys.REDIS_COMMENTS_KEY, comment.getTopicId()));
  }

  @After("com.lckclub.hook.CommentServiceHook.delete()")
  public void commentDelete(JoinPoint joinPoint) {
    Comment comment = (Comment) joinPoint.getArgs()[0];
    redisService.delString(String.format(RedisKeys.REDIS_COMMENTS_KEY, comment.getTopicId()));
  }

  // ---------- comment cache end ----------

  // ---------- user cache start ----------

  @Around("com.lckclub.hook.UserServiceHook.selectByUsername()")
  public Object userSelectByUsername(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    String username = (String) proceedingJoinPoint.getArgs()[0];
    String userJson = redisService.getString(String.format(RedisKeys.REDIS_USER_USERNAME_KEY, username));
    if (userJson != null) {
      // 带泛型转换, 这里如果不带泛型转换，会报错
      return JsonUtil.jsonToObject(userJson, User.class);
    } else {
      Object returnValue = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
      redisService.setString(String.format(RedisKeys.REDIS_USER_USERNAME_KEY, username), JsonUtil.objectToJson
          (returnValue));
      return returnValue;
    }
  }

  @Around("com.lckclub.hook.UserServiceHook.selectByEmail()")
  public Object userSelectByEmail(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    String email = (String) proceedingJoinPoint.getArgs()[0];
    String userJson = redisService.getString(String.format(RedisKeys.REDIS_USER_EMAIL_KEY, email));
    if (userJson != null) {
      // 带泛型转换, 这里如果不带泛型转换，会报错
      return JsonUtil.jsonToObject(userJson, User.class);
    } else {
      Object returnValue = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
      redisService.setString(String.format(RedisKeys.REDIS_USER_EMAIL_KEY, email), JsonUtil.objectToJson(returnValue));
      return returnValue;
    }
  }

  @Around("com.lckclub.hook.UserServiceHook.selectByMobile()")
  public Object userSelectByMobile(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    String mobile = (String) proceedingJoinPoint.getArgs()[0];
    String userJson = redisService.getString(String.format(RedisKeys.REDIS_USER_MOBILE_KEY, mobile));
    if (userJson != null) {
      // 带泛型转换, 这里如果不带泛型转换，会报错
      return JsonUtil.jsonToObject(userJson, User.class);
    } else {
      Object returnValue = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
      redisService.setString(String.format(RedisKeys.REDIS_USER_MOBILE_KEY, mobile), JsonUtil.objectToJson
          (returnValue));
      return returnValue;
    }
  }

  @Around("com.lckclub.hook.UserServiceHook.selectByToken()")
  public Object userSelectByToken(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    String token = (String) proceedingJoinPoint.getArgs()[0];
    String userJson = redisService.getString(String.format(RedisKeys.REDIS_USER_TOKEN_KEY, token));
    if (userJson != null) {
      // 带泛型转换, 这里如果不带泛型转换，会报错
      return JsonUtil.jsonToObject(userJson, User.class);
    } else {
      Object returnValue = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
      redisService.setString(String.format(RedisKeys.REDIS_USER_TOKEN_KEY, token), JsonUtil.objectToJson(returnValue));
      return returnValue;
    }
  }

  @Around("com.lckclub.hook.UserServiceHook.selectById()")
  public Object userSelectById(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    Integer id = (Integer) proceedingJoinPoint.getArgs()[0];
    String userJson = redisService.getString(String.format(RedisKeys.REDIS_USER_ID_KEY, id));
    if (userJson != null) {
      // 带泛型转换, 这里如果不带泛型转换，会报错
      return JsonUtil.jsonToObject(userJson, User.class);
    } else {
      Object returnValue = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
      redisService.setString(String.format(RedisKeys.REDIS_USER_ID_KEY, id), JsonUtil.objectToJson(returnValue));
      return returnValue;
    }
  }

  @After("com.lckclub.hook.UserServiceHook.delRedisUser()")
  public void userDelRedisUser(JoinPoint joinPoint) {
    User user = (User) joinPoint.getArgs()[0];
    redisService.delString(String.format(RedisKeys.REDIS_USER_ID_KEY, user.getId()));
    redisService.delString(String.format(RedisKeys.REDIS_USER_USERNAME_KEY, user.getUsername()));
    redisService.delString(String.format(RedisKeys.REDIS_USER_TOKEN_KEY, user.getToken()));
    redisService.delString(String.format(RedisKeys.REDIS_USER_MOBILE_KEY, user.getMobile()));
    redisService.delString(String.format(RedisKeys.REDIS_USER_EMAIL_KEY, user.getEmail()));
  }

  // ---------- user cache end ----------

  class RedisKeys {
    public static final String REDIS_TOPIC_KEY = "lckclub_topic_%s"; // 后面还要拼上话题的id
    public static final String REDIS_TOPIC_VIEW_IP_ID_KEY = "lckclub_topic_view_ip_%s_topic_%s"; // 需要格式化字符串填充上ip地址跟话题id
    public static final String REDIS_COMMENTS_KEY = "lckclub_comments_%s"; // 后面还要拼上话题的id

    public static final String REDIS_USER_USERNAME_KEY = "lckclub_user_username_%s"; // 后面还要拼上用户名
    public static final String REDIS_USER_TOKEN_KEY = "lckclub_user_token_%s"; // 后面还要拼上用户的token
    public static final String REDIS_USER_MOBILE_KEY = "lckclub_user_mobile_%s"; // 后面还要拼上用户的mobile
    public static final String REDIS_USER_EMAIL_KEY = "lckclub_user_email_%s"; // 后面还要拼上用户的email
    public static final String REDIS_USER_ID_KEY = "lckclub_user_id_%s"; // 后面还要拼上用户的id
  }
}
