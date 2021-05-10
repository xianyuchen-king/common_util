package com.chenyqx.util.jdbc;

import com.chenyqx.util.redis.RedisReentrantLockUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommonUtilApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void test1() throws InterruptedException {
        String key1 = "key1";
        String val1 = "val1";
        if(RedisReentrantLockUtil.tryGetLock(key1,val1,100)){
            System.out.println("加锁成功-----1-------");
            if(RedisReentrantLockUtil.tryGetLock(key1,val1,100)){
                System.out.println("加锁成功-----2-------");
//                Thread.sleep(2000);
                if(RedisReentrantLockUtil.releaseLock(key1,val1)){
                    System.out.println("解锁成功-----1-------");
                }else {
                    System.out.println("解锁失败");
                };

                if(RedisReentrantLockUtil.releaseLock(key1,val1)){
                    System.out.println("解锁成功-----2-------");
                }else {
                    System.out.println("解锁失败");
                };
            }else {
                System.out.println("加锁失败-----2-------");
            }
        }else {
            System.out.println("加锁失败-----1-------");
        };
    }

    @Test
    void test2() throws InterruptedException {
        String key1 = "key2";
        String val1 = "val2";
        if(RedisReentrantLockUtil.tryGetLock(key1,val1,100)){
            System.out.println("加锁成功-----1-------");
            if(RedisReentrantLockUtil.tryGetLock(key1,val1,100)){
                System.out.println("加锁成功-----2-------");
                Thread.sleep(2000);
                if(RedisReentrantLockUtil.releaseLock(key1,val1)){
                    System.out.println("解锁成功-----1-------");
                }else {
                    System.out.println("解锁失败");
                };

                if(RedisReentrantLockUtil.releaseLock(key1,val1)){
                    System.out.println("解锁成功-----2-------");
                }else {
                    System.out.println("解锁失败");
                };
            }else {
                System.out.println("加锁失败-----2-------");
            }
        }else {
            System.out.println("加锁失败-----1-------");
        };
    }

}
