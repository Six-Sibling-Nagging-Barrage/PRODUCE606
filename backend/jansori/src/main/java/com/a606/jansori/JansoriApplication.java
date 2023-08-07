package com.a606.jansori;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class JansoriApplication {

  public static void main(String[] args) {
    SpringApplication.run(JansoriApplication.class, args);
  }
}
