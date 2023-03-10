package com.jalja.common.component.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.util.StringUtils;

/**
 * redisson 中常用得加锁模式
 * @author XL@doublefs.com
 * @date 1/18/23
 */
public class RedisLockComponent implements DistributedLock {
  private RedissonClient redissonClient;
  private String projectPrefix;
  public RedisLockComponent(RedissonClient redissonClient, String projectPrefix) {
    this.redissonClient = redissonClient;
    this.projectPrefix = projectPrefix;
  }
  @Override
  public void lock(String key) {
    RLock rLock=redissonClient.getLock(prefix(key));
    rLock.lock();
  }

  @Override
  public boolean tryLock(String key) {
    RLock rLock=redissonClient.getLock(prefix(key));
    return rLock.tryLock();
  }

  @Override
  public boolean tryLock(String key,long waitTime){
    RLock rLock=redissonClient.getLock(prefix(key));
    try {
      return rLock.tryLock(waitTime,TimeUnit.SECONDS);
    }catch (InterruptedException e){
      e.printStackTrace();
      throw new RuntimeException("获取锁异常");
    }
  }

  @Override
  public boolean tryLock(String key, long leaseTime, long time) {
    RLock rLock=redissonClient.getLock(prefix(key));
    try {
      return rLock.tryLock(leaseTime,time,TimeUnit.SECONDS);
    }catch (InterruptedException e){
      e.printStackTrace();
      throw new RuntimeException("获取锁异常");
    }
  }

  @Override
  public void unlock(String key) {
    RLock rLock=redissonClient.getLock(prefix(key));
    if (rLock!=null && rLock.isHeldByCurrentThread()){
      rLock.unlock();
    }
  }

  @Override
  public void lock(String key, long leaseTime) {
    RLock lock = redissonClient.getLock(prefix(key));
    lock.lock(leaseTime, TimeUnit.SECONDS);
  }

  @Override
  public void multiLock(String... key) {
    RLock [] rLocks=new RLock[key.length];
    for (int i=0;i<key.length;i++){
     rLocks[i]=redissonClient.getLock(prefix(key[i]));
    }
    redissonClient.getMultiLock(rLocks).lock();
  }

  @Override
  public void multiUnLock(String... key) {
    RLock [] rLocks=new RLock[key.length];
    for (int i=0;i<key.length;i++){
      rLocks[i]=redissonClient.getLock(prefix(key[i]));
    }
    redissonClient.getMultiLock(rLocks).unlock();
  }


  private String prefix(String key){
    if (!StringUtils.hasLength(key)){
      throw new RuntimeException("key不能为空");
    }
    return projectPrefix +":"+key;
  }
}
