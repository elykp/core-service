package com.elykp.coreservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CoreServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(CoreServiceApplication.class, args);
  }

  @Bean
  @LoadBalanced
  public RestTemplate getRestTemplate() {
    return new RestTemplate();
  }
}
