package com.bookmymovie.cinema;

import com.google.cloud.spring.data.datastore.repository.config.EnableDatastoreRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDatastoreRepositories
public class MovieSpringBootApplication {

  public static void main(String[] args) {
    SpringApplication.run(MovieSpringBootApplication.class, args);
  }
}
