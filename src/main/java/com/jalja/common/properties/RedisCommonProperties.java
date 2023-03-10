package com.jalja.common.properties;

import com.jalja.common.model.UrlModel;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author XL@doublefs.com
 * @date 1/18/23
 */
@ConfigurationProperties("spring.common.redis")
public class RedisCommonProperties {
  /**
   * 模式
   * single（单机）
   * cluster(集群)
   * sentry（哨兵）
   */
  private String pattern;
  /**
   * 地址
   */
  private List<UrlModel> urls;
  /**
   * 密码
   */
  private String password;
  /**
   * 数据库 默认 db0 (集群模式下该配置无效)
   */
  private Integer dbNum= BigDecimal.ZERO.intValue();
  /**
   *项目标识(英文，每个项目都要有一个唯一标识)
   */
  private String projectName;

  public String getPattern() {
    return pattern;
  }

  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  public List<UrlModel> getUrls() {
    return urls;
  }

  public void setUrls(List<UrlModel> urls) {
    this.urls = urls;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public Integer getDbNum() {
    return dbNum;
  }

  public void setDbNum(Integer dbNum) {
    this.dbNum = dbNum;
  }
}
