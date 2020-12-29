package cn.xinglongfei.blog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Phoenix on 2020/12/29
 */
@Configuration
public class ThreadPoolConfig {

    // 核心线程池大小
    @Value("${thread.corepoolsize}")
    private final int corePoolSize = 50;

    // 最大可创建的线程数
    @Value("${thread.maxpoolsize}")
    private final int maxPoolSize = 200;

    // 队列最大长度
    @Value("${thread.queuecapacity}")
    private final int queueCapacity = 1000;

    // 线程池维护线程所允许的空闲时间
    @Value("${thread.keepaliveseconds}")
    private final int keepAliveSeconds = 300;

    @Bean(name = "threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor()
    {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(maxPoolSize);
        executor.setCorePoolSize(corePoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        // 线程池对拒绝任务(无线程可用)的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

}
