package com.github.istock.conf;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfiguration {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();

        List<CaffeineCache> caffeineCaches = new ArrayList<>();

        caffeineCaches.add(new CaffeineCache("cache2Minute",
                Caffeine.newBuilder()
                        .initialCapacity(50)
                        .maximumSize(30000)//最大存储数量
                        .expireAfterWrite(2, TimeUnit.MINUTES)//过期时间2 分钟
                        .build()));
        cacheManager.setCaches(caffeineCaches);
        return cacheManager;
    }
}
