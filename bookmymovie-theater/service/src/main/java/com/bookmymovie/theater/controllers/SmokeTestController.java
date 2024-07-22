package com.bookmymovie.theater.controllers;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/smoke")
@Slf4j
public class SmokeTestController {

  @RequestMapping(path = "/")
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
