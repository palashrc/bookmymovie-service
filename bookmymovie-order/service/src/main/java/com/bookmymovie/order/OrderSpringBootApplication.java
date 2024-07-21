package com.bookmymovie.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.google.cloud.spring.data.datastore.repository.config.EnableDatastoreRepositories;

@SpringBootApplication
@EnableDatastoreRepositories
public class OrderSpringBootApplication {

  public static void main(String[] args) {
    SpringApplication.run(OrderSpringBootApplication.class, args);
  }
}
