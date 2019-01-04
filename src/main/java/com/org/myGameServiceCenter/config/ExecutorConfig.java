package com.org.myGameServiceCenter.config;

import com.org.myGameServiceCenter.config.base.MyBaseVisiableThreadPoolTaskExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;

/**
 * @author nielong123
 * @date 2018/9/25
 */

@Configuration
@EnableAsync
@Slf4j
public class ExecutorConfig {

    @Bean
    public Executor asyncServiceExecutor() {
        log.info("start asyncServiceExecutor");
        MyBaseVisiableThreadPoolTaskExecutor executor = new MyBaseVisiableThreadPoolTaskExecutor();
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(8);
        executor.setMaxPoolSize(16);
        executor.setQueueCapacity(99999);
        executor.setThreadNamePrefix("async-service-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
