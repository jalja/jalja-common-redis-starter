package com.jalja.common;


import com.jalja.common.component.redis.RedisComponent;
import com.jalja.common.component.redis.RedisLockComponent;
import com.jalja.common.component.RedissonClientComponent;
import com.jalja.common.properties.RedisCommonProperties;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author XL@doublefs.com
 * @date 1/18/23
 */
@Configuration
@EnableConfigurationProperties(RedisCommonProperties.class)
public class CommonAutoConfiguration {
  @Autowired
  private RedisCommonProperties redisCommonProperties;

  @Bean
  public RedissonClient redissonClient(){
    return new RedissonClientComponent().redissonClient(redisCommonProperties);
  }
  @Bean
  public RedisComponent redisComponent(){
    return new RedisComponent(redissonClient(),redisCommonProperties.getProjectName());
  }
  @Bean
  public RedisLockComponent redisLockComponent(){
    return new RedisLockComponent(redissonClient(),redisCommonProperties.getProjectName());
  }
}
