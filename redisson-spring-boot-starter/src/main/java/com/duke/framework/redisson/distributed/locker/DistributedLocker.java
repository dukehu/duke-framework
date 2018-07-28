package com.duke.framework.redisson.distributed.locker;

import org.redisson.api.RLock;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created duke on 2018/7/27
 */
@Component
public interface DistributedLocker {

    RLock lock(String lockKey);

    RLock lock(String lockKey, int timeout);

    RLock lock(String lockKey, TimeUnit unit, int timeout);

    boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime);

    void unlock(String lockKey);

    void unlock(RLock lock);

}
