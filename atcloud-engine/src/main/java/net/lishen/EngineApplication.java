package net.lishen;

import net.lishen.util.SpringContextHolder;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph
 * @Project：gpcloud-meter
 * @name：EngineApplication
 * @Date：2023-12-29 11:27
 * @Filename：EngineApplication
 */
@SpringBootApplication
@MapperScan("net.lishen.mapper")
@EnableTransactionManagement
@EnableFeignClients
@EnableDiscoveryClient
@EnableAsync
public class EngineApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(EngineApplication.class, args);
        SpringContextHolder.setApplicationContext(applicationContext);
    }
}
