package com.duke.framework.redisson.distributed.locker.impl;

import com.duke.framework.redisson.distributed.locker.DistributedLocker;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created duke on 2018/7/27
 */
@Component
public class DistributedLockerImpl implements DistributedLocker {

    private RedissonClient redissonClient;

    public DistributedLockerImpl(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public RLock lock(String lockKey) {
        RLock rLock = redissonClient.getLock(lockKey);
        rLock.lock();
        return rLock;
    }

    @Override
    public RLock lock(String lockKey, int timeout) {
        RLock rLock = redissonClient.getLock(lockKey);
        rLock.lock(timeout, TimeUnit.SECONDS);
        return rLock;
    }

    @Override
    public RLock lock(String lockKey, TimeUnit unit, int timeout) {
        RLock rLock = redissonClient.getLock(lockKey);
        rLock.lock(timeout, unit);
        return rLock;
    }

    @Override
    public boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) {
        RLock rLock = redissonClient.getLock(lockKey);
        try {
            return rLock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public void unlock(String lockKey) {
        RLock rLock = redissonClient.getLock(lockKey);
        rLock.unlock();
    }

    @Override
    public void unlock(RLock lock) {
        lock.unlock();
    }
}
