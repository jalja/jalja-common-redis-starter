package com.jalja.common;

import com.alibaba.fastjson2.JSON;
import com.jalja.common.component.redis.RedisComponent;
import com.jalja.common.component.RedissonClientComponent;
import com.jalja.common.model.UrlModel;
import com.jalja.common.model.enums.RedisPatternEnum;
import com.jalja.common.properties.RedisCommonProperties;
import java.util.List;
import org.junit.Test;
import org.redisson.api.RedissonClient;

/**
 * @author XL@doublefs.com
 * @date 1/18/23
 */
public class RedisServiceTest {

  private static RedisComponent redisComponent;
  static {
    RedisCommonProperties redisCommonProperties=new RedisCommonProperties();
    redisCommonProperties.setPassword(null);
    redisCommonProperties.setProjectName("test");
    redisCommonProperties.setPattern(RedisPatternEnum.SINGLE.getValue());
    UrlModel urlModel=new UrlModel();
    urlModel.setHost("cn-test-2.cache.doublefs.com");
    urlModel.setPort(6379);
    redisCommonProperties.setUrls(List.of(urlModel));
    RedissonClient redissonClient= new RedissonClientComponent().redissonClient(redisCommonProperties);
    redisComponent=new RedisComponent(redissonClient,redisCommonProperties.getProjectName());
  }
  @Test
  public void setValue() {
    UrlModel urlModel=new UrlModel();
    urlModel.setHost("cn-test-2.cache.doublefs.com");
    urlModel.setPort(6379);
    redisComponent.setValue("name",List.of(urlModel));
    System.out.println(JSON.toJSON(redisComponent.getValue("name")));
  }
  @Test
  public void setValue2() {
    redisComponent.setValue("name","中国",20L);
    System.out.println(JSON.toJSON(redisComponent.getValue("name")));
  }
   @Test
  public void incrementAndGet(){
     Long result= redisComponent.incrementAndGet("abc");
     System.out.println("result="+result);
  }
  @Test
  public void addList(){
    redisComponent.addList("name",List.of("A","B","C"));
    System.out.println(JSON.toJSON(redisComponent.getList("name")));
  }
}
