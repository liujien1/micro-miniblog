package website.liujie.util.jedis;

import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Author: 刘杰.
 * Date: 2017-12-22.
 * Describe:
 */

public class RedisLockUtil {

    //public RedisUtil redisUtil = (RedisUtil) ContextLoader.getCurrentWebApplicationContext().getBean("redisUtil");
    public static RedisLockUtil redisLockUtil;

    public RedisUtil redisUtil;

    private static int EXPIRED_TIME = 10;

    public RedisLockUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    public static RedisLockUtil getRedisLockUtil(RedisUtil redisUtil) {
        if (redisLockUtil == null) {
            redisLockUtil = new RedisLockUtil(redisUtil);
        }
        return redisLockUtil;
    }

    /**
     * 锁在给定的等待时间内空闲，则获取锁成功 返回true， 否则返回false
     *
     * @param key
     * @param timeout 如果timeout=0,取不到锁时,不等待,直接返回.
     * @param unit
     * @return
     */
    public boolean tryLock(String key, long timeout, TimeUnit unit) {
        boolean isLock = false;
        long nano = System.nanoTime();
        do {
            Long i = redisUtil.setnx(key, key);
            if (i == 1) {
                redisUtil.expired(key, EXPIRED_TIME);
                isLock = true;
                break;
            } else { // 存在锁
//				String desc = redisUtil.get(key);
            }
            if (timeout == 0) { // 取不到锁时,不等待,直接返回.
                break;
            }
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while ((System.nanoTime() - nano) < unit.toNanos(timeout));// 取不到锁时等待,直到timeout
        return isLock;
    }

    /**
     * 锁
     *
     * @param key
     * @param timeout
     * @param unit
     * @return
     */
    public boolean lock(String key, long timeout, TimeUnit unit) {
        boolean isTimeOut = false;
        long isSucc = 0;
        while (1 != isSucc) {
            long nano = System.nanoTime();
            isSucc = redisUtil.setnx(key, String.valueOf(nano));
            if (isSucc == 1) {
                redisUtil.expired(key, EXPIRED_TIME);
                break;
            } else if ((System.nanoTime() - nano) > unit.toNanos(timeout)) {
                isTimeOut = true;
                break;
            }
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return isTimeOut ? false : true;
    }

    /**
     * 释放锁
     *
     * @param key
     */
    public void unLock(String key) {
        redisUtil.del(key);
    }


}
