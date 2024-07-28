package com.bookmymovie.orchestrator;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrchestratorSpringBootApplicationTest {
  @Value("${spring.application.name}")
  String applicationName;

  @Autowired TestRestTemplate template;

  @ParameterizedTest
  @CsvSource({
    "/, This is auto-generated '%s' application",
    "/hello, Hello Anonymous!",
    "/hello/Sabre, Hello Sabre!",
    "/smoke, OK"
  })
  public void shouldReturnMessageFromPath(String path, String response) {
    // when
    ResponseEntity<String> responseEntity = template.getForEntity(path, String.class);
    // then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody()).isEqualTo(String.format(response, applicationName));
  }
}
