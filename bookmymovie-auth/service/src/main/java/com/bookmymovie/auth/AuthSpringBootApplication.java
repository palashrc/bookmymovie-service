package com.bookmymovie.auth;

import com.google.cloud.spring.data.datastore.repository.config.EnableDatastoreRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDatastoreRepositories
public class AuthSpringBootApplication {

  public static void main(String[] args) {
    SpringApplication.run(AuthSpringBootApplication.class, args);
  }
}
