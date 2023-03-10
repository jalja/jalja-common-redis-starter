package com.jalja.common.component.redis;

/**
 * @author XL@doublefs.com
 * @date 1/19/23
 */
public interface DistributedLock {
  /**
   * 加锁
   * @param key
   */
  void lock(String key);

  /**
   * 尝试加锁， 直接返回获取锁的结果
   * @param key
   * @return
   */
  boolean tryLock(String key);
  /**
   * 解锁
   * @param key
   */
  void unlock(String key);

  /**
   * 尝试加锁，等待 waitTime 时间后
   * @param key
   * @param waitTime 等待加锁的时间（秒）
   * @return 返回加锁的结果
   */
  boolean tryLock(String key,long waitTime );

  /**
   *  尝试加锁，最多等待 waitTime 秒，上锁以后 leaseTime 秒自动解锁
   * @param key
   * @param waitTime  最多等待加锁的时间 （秒）
   * @param leaseTime 上锁以后自动解锁 （秒）
   * @return
   */
  boolean tryLock(String key,long waitTime, long leaseTime );

  /**
   * 加锁时 需要设置锁的释放时间
   * @param key
   * @param leaseTime 锁的释放时间（秒）
   */
  void lock(String key, long leaseTime);

  /**
   * 同时添加多把锁
   * @param key
   */
  void multiLock(String ...key);
  /**
   * 同时释放多把锁
   * @param key
   */
  void multiUnLock(String ...key);

}
