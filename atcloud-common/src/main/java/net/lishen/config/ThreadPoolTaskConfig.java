package net.lishen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.config
 * @Project：gpcloud-meter
 * @name：ThreadPoolTaskConfig
 * @Date：2024-01-20 22:39
 * @Filename：ThreadPoolTaskConfig
 */
@Configuration
@EnableAsync
public class ThreadPoolTaskConfig {

    @Bean("threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor(){

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //线程池创建的核心线程数，线程池维护线程的最少数量，即使没有任务需要执行，也会一直存活
        //如果设置allowCoreThreadTimeout=true（默认false）时，核心线程会超时关闭
        executor.setCorePoolSize(16);
//        executor.setAllowCoreThreadTimeOut(true);

        //缓存队列（阻塞队列）当核心线程数达到最大时，新任务会放在队列中排队等待执行
        executor.setQueueCapacity(1024);

        //最大线程池数量，当线程数>=corePoolSize，且任务队列已满时。线程池会创建新线程来处理任务
        //当线程数=maxPoolSize，且任务队列已满时，线程池会拒绝处理任务而抛出异常
        executor.setMaxPoolSize(64);
        //当线程空闲时间达到keepAliveTime时，线程会退出，直到线程数量=corePoolSize
        //允许线程空闲时间60秒，当maxPoolSize的线程在空闲时间到达的时候销毁
        //如果allowCoreThreadTimeout=true，则会直到线程数量=0
        executor.setKeepAliveSeconds(30);

        executor.setThreadNamePrefix("joseph的自定义线程池");


        //拒绝策略
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CallerRunsPolicy()：交由调用方线程运行，比如 main 线程；如果添加到线程池失败，那么主线程会自己去执行该任务，不会等待线程池中的线程去执行
        //AbortPolicy()：该策略是线程池的默认策略，如果线程池队列满了丢掉这个任务并且抛出RejectedExecutionException异常。
        //DiscardPolicy()：如果线程池队列满了，会直接丢掉这个任务并且不会有任何异常
        //DiscardOldestPolicy()：丢弃队列中最老的任务，队列满了，会将最早进入队列的任务删掉腾出空间，再尝试加入队列
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());

        executor.initialize();

        return executor;

    }

    @Bean("testExecutor")
    public Executor stressTestExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置线程池的核心线程数：线程池创建时候初始化的线程数
        executor.setCorePoolSize(10);
        // 设置线程池的最大线程数：线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
        executor.setMaxPoolSize(20);
        // 缓冲队列：用来缓冲执行任务的队列
        executor.setQueueCapacity(500);
        // 设置线程池中除了核心线程之外的线程的最长存活时间, 允许线程的空闲时间60秒：当超过了空闲时间到达之后会被销毁
        executor.setKeepAliveSeconds(60);
        // 设置线程池中所有线程的名字前缀：设置好了之后可以方便定位处理任务所在的线程池
        executor.setThreadNamePrefix("joseph-test-");
        // 缓冲队列满了之后的拒绝策略：由调用线程处理（一般是主线程）
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        executor.initialize();
        return executor;
    }
}
