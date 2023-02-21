package com.platformyun.plus.provider;

import com.platformyun.plus.configuration.DubboSentinelConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(scanBasePackageClasses = {ProviderAdminApplication.class, DubboSentinelConfiguration.class})
@MapperScan(basePackages = "com.platformyun.plus.provider.mapper")
public class ProviderAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderAdminApplication.class,args);
    }
}
