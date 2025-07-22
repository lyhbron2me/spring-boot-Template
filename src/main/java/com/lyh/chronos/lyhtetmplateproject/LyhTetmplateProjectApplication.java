package com.lyh.chronos.lyhtetmplateproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

// (exclude = {DataSourceAutoConfiguration.class}) TODO 启动时排除数据源配置，因为项目没有数据源
@SpringBootApplication
public class LyhTetmplateProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(LyhTetmplateProjectApplication.class, args);
    }

}
