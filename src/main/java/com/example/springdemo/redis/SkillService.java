package com.example.springdemo.redis;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class SkillService {
    @Resource
    RedissonClient redissonClient;

    private final static String LOCK_KEY = "RESOURCE_KEY";
    int n = 500;

    public void seckill() {
        //定义锁
        RLock lock = redissonClient.getLock(LOCK_KEY);
        //lock.lock();
        try {
            //尝试加锁,最大等待时间300毫秒，上锁30毫秒自动解锁
            if (lock.tryLock(300, 30, TimeUnit.MILLISECONDS)) {
                log.info("线程:" + Thread.currentThread().getName() + "获得了锁");
                log.info("剩余数量:{}", --n);
            }
        } catch (Exception e) {
            log.error("程序执行异常:{}", e);
        } finally {
            log.info("线程:" + Thread.currentThread().getName() + "准备释放锁");
            //释放锁
            lock.unlock();
        }
    }
}

