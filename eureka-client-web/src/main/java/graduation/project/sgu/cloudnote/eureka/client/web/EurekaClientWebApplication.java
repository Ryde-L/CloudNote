package graduation.project.sgu.cloudnote.eureka.client.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("graduation.project.sgu.cloudnote.eureka.client.web.dao.mybatis")
public class EurekaClientWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaClientWebApplication.class, args);
    }

}
