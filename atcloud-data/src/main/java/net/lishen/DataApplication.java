package net.lishen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph
 * @Project：atcloud-meter
 * @name：EngineApplication
 * @Date：2023-12-29 11:27
 * @Filename：EngineApplication
 */
@SpringBootApplication
@MapperScan("net.lishen.mapper")
@EnableTransactionManagement
@EnableFeignClients
@EnableDiscoveryClient
public class DataApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataApplication.class,args);
    }
}
