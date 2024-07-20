package com.bookmymovie.auth.controllers;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmokeTestController {
  private static final Logger log = (Logger) LoggerFactory.getLogger(SmokeTestController.class);

  @RequestMapping(path = "/smoke")
  public ResponseEntity<String> smokeTest() {
    try {
      log.info("Running smoke tests...");
      // do what is relevant to your application here
      log.info("Smoke tests passed.");
      return ResponseEntity.ok("OK");
    } catch (Exception e) {
      log.error(
          "Error occurred while running smoke tests; service is unable to handle traffic.", e);
      return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
    }
  }
}
