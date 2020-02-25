package graduation.project.sgu.cloudnote.administrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class AdministratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdministratorApplication.class, args);
    }

}
