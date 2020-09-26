package com.cloudnote.note;

import cn.dev33.satoken.spring.SaTokenSetup;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.cloudnote.note.dao.mapper")
@ServletComponentScan("com.cloudnote.note.filter")
@ComponentScan(basePackages = {"com.cloudnote.note.config","com.cloudnote.note.controller","com.cloudnote.note.service","com.cloudnote.note.satoken"})
@SaTokenSetup
public class NoteApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoteApplication.class, args);
    }


}

