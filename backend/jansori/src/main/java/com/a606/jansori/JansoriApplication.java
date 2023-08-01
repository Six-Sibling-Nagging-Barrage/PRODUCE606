package com.a606.jansori;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class JansoriApplication {

    public static void main(String[] args) {
//        SpringApplication.run(JansoriApplication.class, args);
        new SpringApplicationBuilder(JansoriApplication.class).properties(
                        "spring.config.name:application",
                        "spring.config.location:optional:/app/config/springboot-jansori/")
                .build().run(args);
    }

}
