package cn.geminiplanet.ccms;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSwagger2Doc
@SpringBootApplication
public class CCMSApplication {

    public static void main(String[] args) {
        SpringApplication.run(CCMSApplication.class, args);
    }

}
