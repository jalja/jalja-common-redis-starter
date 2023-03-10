package com.jalja.common.component.redis;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.util.StringUtils;

/**
 * Redis 的常用操作
 * @author XL@doublefs.com
 * @date 1/18/23
 */
public class RedisComponent {
  private RedissonClient redissonClient;
  private String projectPrefix;
  public RedisComponent(RedissonClient redissonClient, String projectPrefix) {
    this.redissonClient = redissonClient;
    this.projectPrefix = projectPrefix;
  }
  /**
   * set 一个没有过期时间的 key
   * @param key
   * @param t
   * @param <T>
   */
  public <T> void setValue(String key,T t){
    redissonClient.getBucket(prefix(key)).set(t);
  }
  /**
   * 设置有过期时间的 key
   * @param key
   * @param expireTime 单位秒
   */
  public <T> void setValue(String key,T t ,Long expireTime){
    redissonClient.getBucket(prefix(key)).set(t,expireTime, TimeUnit.SECONDS);
  }
  /**
   * 给 key 设置过期时间
   * @param key
   * @param expireTime 单位秒
   */
  public void expireTime(String key, Long expireTime){
    redissonClient.getBucket(prefix(key)).expire(expireTime,TimeUnit.SECONDS);
  }
  /**
   * 自增
   * @param key
   * @return
   */
  public Long incrementAndGet(String key){
    RAtomicLong rAtomicLong= redissonClient.getAtomicLong(prefix(key));
    return rAtomicLong.incrementAndGet();
  }
  /**
   * 根据 key 查询缓存中的数据
   * @param key
   * @param <T>
   * @return
   */
  public <T> T getValue(String key){
    return (T) redissonClient.getBucket(prefix(key)).get();
  }

  /**
   * 删除 key
   * @param key
   */
  public void removeKey(String key){
   redissonClient.getBucket(prefix(key)).delete();
  }


  /**
   * list 添加一个元素
   * @param key
   * @param t
   * @param <T>
   */
  public <T> void addList(String key,T t){
    redissonClient.getList(prefix(key)).add(t);
  }
  /**
   * 按照位置添加元素
   * @param key
   * @param idx 位置
   * @param t
   * @param <T>
   */
  public <T> void addList(String key,int idx,T t){
    redissonClient.getList(key).add(idx,t);
  }
  /**
   * 按照位置添加元素 ，可以设置过期时间
   * @param key
   * @param idx 位置
   * @param t
   * @param expireTime 过期时间（秒）
   * @param <T>
   */
  public <T> void addList(String key,int idx,T t,Long expireTime){
    RList<T> rList=redissonClient.getList(prefix(key));
    rList.add(idx,t);
    rList.expire(expireTime,TimeUnit.SECONDS);
  }


  /**
   * list 添加一个元素 且设置过期时间
   * @param key
   * @param t
   * @param expireTime 过期时间（秒）
   * @param <T>
   */
  public <T> void addList(String key,T t,Long expireTime){
    RList<T> rList=redissonClient.getList(prefix(key));
    rList.add(t);
    rList.expire(expireTime,TimeUnit.SECONDS);
  }
  /**
   * list 添加多个元素
   * @param key
   * @param list
   * @param <T>
   */
  public <T> void addList(String key, Collection<T> list){
    redissonClient.getList(prefix(key)).addAll(list);
  }

  /**
   * list 添加多个元素且设置过期时间
   * @param key
   * @param list
   * @param <T>
   */
  public <T> void addList(String key, Collection<T> list,Long expireTime){
    RList<T> rList=redissonClient.getList(prefix(key));
    rList.addAll(list);
    rList.expire(expireTime,TimeUnit.SECONDS);
  }

  /**
   * 获取集合中所有的元素
   * @param key
   * @param <T>
   * @return
   */
  public <T> List<T> getList(String key){
    return (List<T>) redissonClient.getList(prefix(key)).readAll();
  }

  /**
   * 删除集合
   * @param key
   */
  public void removeList(String key){
    redissonClient.getList(prefix(key)).delete();
  }

  private String prefix(String key){
    if (!StringUtils.hasLength(key)){
      throw new RuntimeException("key不能为空");
    }
    return projectPrefix +":"+key;
  }

}
