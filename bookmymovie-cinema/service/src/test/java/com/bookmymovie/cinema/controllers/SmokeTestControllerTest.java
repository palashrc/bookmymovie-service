package com.bookmymovie.auth.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.bookmymovie.movie.controllers.SmokeTestController;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SmokeTestControllerTest {
  SmokeTestController smokeTestController = new SmokeTestController();

  @Test
  public void shouldReturnSuccessStatusWhenSmokeTestCalled() {
    // when
    ResponseEntity<String> response = smokeTestController.smokeTest();

    // then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo("OK");
  }
}
