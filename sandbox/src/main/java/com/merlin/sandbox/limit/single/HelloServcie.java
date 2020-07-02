package com.merlin.sandbox.limit.single;

import com.google.common.util.concurrent.RateLimiter;

public class HelloServcie {
    // 令牌桶
    RateLimiter rateLimiter = RateLimiter.create(10); // qps=10

    public void doRequest() {
        if(rateLimiter.tryAcquire()) {
            System.out.println("请求成功!");
        } else {
            System.out.println("请求够多够多， 请稍后重试!");
        }
    }
}
