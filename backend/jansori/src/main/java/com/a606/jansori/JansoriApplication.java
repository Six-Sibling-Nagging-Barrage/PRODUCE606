package com.a606.jansori;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class, WebMvcAutoConfiguration.class })
public class JansoriApplication {

    public static void main(String[] args) {
        SpringApplication.run(JansoriApplication.class, args);
    }

}
