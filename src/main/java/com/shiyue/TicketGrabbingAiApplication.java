package com.shiyue;

import org.dromara.easyes.spring.annotation.EsMapperScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EsMapperScan("com.shiyue.es.mapper")
@MapperScan("com.shiyue.mapper")
@SpringBootApplication
public class TicketGrabbingAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketGrabbingAiApplication.class, args);
    }

}
