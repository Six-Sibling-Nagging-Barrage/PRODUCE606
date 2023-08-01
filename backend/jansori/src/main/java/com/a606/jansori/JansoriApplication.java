package com.a606.jansori;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class JansoriApplication {

    public static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "/app/config/springboot-jansori/application.yml,"
            + "/app/config/sroom/real-application-release.yml";

    public static void main(String[] args) {
//        SpringApplication.run(JansoriApplication.class, args);
        new SpringApplicationBuilder(JansoriApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
    }

}
