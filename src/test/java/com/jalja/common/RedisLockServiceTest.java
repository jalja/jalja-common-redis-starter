package com.jalja.common;


import com.jalja.common.component.redis.RedisLockComponent;
import com.jalja.common.component.RedissonClientComponent;
import com.jalja.common.model.UrlModel;
import com.jalja.common.model.enums.RedisPatternEnum;
import com.jalja.common.properties.RedisCommonProperties;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.redisson.api.RedissonClient;

/**
 * @author XL@doublefs.com
 * @date 1/18/23
 */
public class RedisLockServiceTest {

  private static RedisLockComponent redisLockComponent;
  static {
    RedisCommonProperties redisCommonProperties=new RedisCommonProperties();
    redisCommonProperties.setPassword(null);
    redisCommonProperties.setProjectName("test");
    redisCommonProperties.setPattern(RedisPatternEnum.SINGLE.getValue());
    UrlModel urlModel=new UrlModel();
    urlModel.setHost("cn-test-2.cache.doublefs.com");
    urlModel.setPort(6379);
    redisCommonProperties.setUrls(List.of(urlModel));
    RedissonClient redissonClient=new RedissonClientComponent().redissonClient(redisCommonProperties);
    redisLockComponent=new RedisLockComponent(redissonClient,redisCommonProperties.getProjectName());
  }

  public static void main(String[] args) {
    Thread thread=new Thread(()->{
      System.out.println(Thread.currentThread().getName()+"00进入");
      redisLockComponent.multiLock("key1","key2");
      System.out.println(Thread.currentThread().getName()+"00执行");
      redisLockComponent.multiUnLock("key1","key2");
      System.out.println(Thread.currentThread().getName()+"00结束");
    });
    thread.start();
    Thread thread2=new Thread(()->{
      System.out.println(Thread.currentThread().getName()+"进入");
      redisLockComponent.multiLock("key1","key2");
      System.out.println(Thread.currentThread().getName()+"执行");
      redisLockComponent.multiUnLock("key1","key2");
      System.out.println(Thread.currentThread().getName()+"结束");
    });

    thread2.start();

  }
}
