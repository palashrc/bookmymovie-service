package com.bookmymovie.auth;

import com.google.cloud.spring.data.datastore.repository.config.EnableDatastoreRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDatastoreRepositories
public class NotificationSpringBootApplication {

  public static void main(String[] args) {
    SpringApplication.run(NotificationSpringBootApplication.class, args);
  }
}
