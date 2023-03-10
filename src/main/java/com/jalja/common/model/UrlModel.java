package com.jalja.common.model;

/**
 * @author XL@doublefs.com
 * @date 1/18/23
 */
public class UrlModel {
  /**
   * IP地址
   */
  private String host;
  /**
   * 端口号
   */
  private Integer port;


  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public Integer getPort() {
    return port;
  }

  public void setPort(Integer port) {
    this.port = port;
  }

}
