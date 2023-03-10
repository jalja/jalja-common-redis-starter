package com.jalja.common.model.enums;

import java.util.Arrays;
import java.util.Optional;
import org.springframework.util.StringUtils;

/**
 * @author XL@doublefs.com
 * @date 1/18/23
 */
public enum RedisPatternEnum {
  SINGLE("single"),
  CLUSTER("cluster"),
  SENTRY("sentry"),
  ;
  private String value;

  RedisPatternEnum(String value) {
    this.value = value;
  }
  public static RedisPatternEnum getEnum(String value){
   Optional<RedisPatternEnum> optional=Arrays.stream(RedisPatternEnum.values()).filter(patternEnum-> StringUtils.endsWithIgnoreCase(patternEnum.value,value)).findAny();
   if (optional.isEmpty()){
     throw new RuntimeException("redis 模式错误,[single,cluster,sentry]");
   }
   return optional.get();
  }
  public String getValue() {
    return value;
  }
}
