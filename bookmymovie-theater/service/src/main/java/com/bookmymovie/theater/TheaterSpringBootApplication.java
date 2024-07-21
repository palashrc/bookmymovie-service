package com.bookmymovie.theater;

import com.google.cloud.spring.data.datastore.repository.config.EnableDatastoreRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDatastoreRepositories
public class TheaterSpringBootApplication {

  public static void main(String[] args) {
    SpringApplication.run(TheaterSpringBootApplication.class, args);
  }
}
