package com.lckclub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

// 不用默认配置的数据源，自己配置
@SpringBootApplication(scanBasePackages = "com.lckclub",
    exclude = {DataSourceAutoConfiguration.class, FlywayAutoConfiguration.class})
@EnableAspectJAutoProxy
public class LckclubApplication {
  public static void main(String[] args) {
    SpringApplication.run(LckclubApplication.class, args);
  }
}
