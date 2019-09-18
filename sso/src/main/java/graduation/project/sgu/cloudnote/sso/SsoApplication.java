package graduation.project.sgu.cloudnote.sso;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("graduation.project.sgu.cloudnote.sso.dao.mapper")

//@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 7200)// 开启redis session ，并且设置session有效时长
public class SsoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsoApplication.class, args);
    }

}
