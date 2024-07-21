package com.bookmymovie.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.google.cloud.spring.data.datastore.repository.config.EnableDatastoreRepositories;

@SpringBootApplication
@EnableDatastoreRepositories
public class PaymentSpringBootApplication {

  public static void main(String[] args) {
    SpringApplication.run(PaymentSpringBootApplication.class, args);
  }
}
