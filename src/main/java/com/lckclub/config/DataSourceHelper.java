package com.lckclub.config;

import com.lckclub.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Configuration
public class DataSourceHelper {

  private Logger log = LoggerFactory.getLogger(DataSourceHelper.class);

  @Autowired
  private SiteConfig siteConfig;

  @PostConstruct
  public void init() {
    if (siteConfig == null) siteConfig = SpringContextUtil.getBean(SiteConfig.class);
    try {
      Class.forName(siteConfig.getDatasource_driver());
      URI uri = new URI(siteConfig.getDatasource_url().replace("jdbc:", ""));
      String host = uri.getHost();
      int port = uri.getPort();
      String path = uri.getPath();
      String query = uri.getQuery();
      Connection connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "?" + query,
          siteConfig.getDatasource_username(), siteConfig.getDatasource_password());
      Statement statement = connection.createStatement();
      statement.executeUpdate("CREATE DATABASE IF NOT EXISTS `" + path.replace("/", "") + "` DEFAULT CHARACTER SET = "
          + "" + "`utf8` COLLATE `utf8_general_ci`;");
      statement.close();
      connection.close();
    } catch (URISyntaxException | ClassNotFoundException | SQLException e) {
      log.error(e.getMessage());
      log.error("创建数据库失败!");
    }
  }
}
