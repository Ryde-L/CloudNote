package graduation.project.sgu.cloudnote.administrator;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("graduation.project.sgu.cloudnote.administrator.dao.mapper")

public class AdministratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdministratorApplication.class, args);
    }

}
