package com.xxxx.crm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("com.xxxx.crm.mapper")
public class Starter extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Starter.class);
    }

//     告诉web容器这就是程序的启动入口
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(Starter.class);
//    }
}
