package com.lyh.lyhtetmplateproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// (exclude = {DataSourceAutoConfiguration.class}) TODO 启动时排除数据源配置，因为项目没有数据源
@SpringBootApplication
@MapperScan("com.lyh.lyhtetmplateproject.mapper")
public class LyhTetmplateProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(LyhTetmplateProjectApplication.class, args);
    }

}
