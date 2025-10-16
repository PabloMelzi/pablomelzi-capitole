package com.pablomelzi.prueba.system;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,properties = "spring.profiles.active=test")
public class PriceControllerSystemTest {

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    private String buildUrl(String date, long productId, long brandId) {
        return String.format("http://localhost:%d/prices?applicationDate=%s&productId=%d&brandId=%d",
                port, date, productId, brandId);
    }

    @Test
    @DisplayName("Test 1: 2020-06-14 10:00 — priceList 1 expected")
    void test1() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                buildUrl("2020-06-14T10:00:00", 35455, 1), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("\"priceList\":1");
    }


    @Test
    @DisplayName("Test 2: 2020-06-14 16:00 — priceList 2 expected")
    void test2() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                buildUrl("2020-06-14T16:00:00", 35455, 1), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("\"priceList\":2");
    }

    @Test
    @DisplayName("Test 3: 2020-06-14 21:00 — priceList 1 expected")
    void test3() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                buildUrl("2020-06-14T21:00:00", 35455, 1), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("\"priceList\":1");
    }

    @Test
    @DisplayName("Test 4: 2020-06-15 10:00 — priceList 3 expected")
    void test4() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                buildUrl("2020-06-15T10:00:00", 35455, 1), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("\"priceList\":3");
    }

    @Test
    @DisplayName("Test 5: 2020-06-16 21:00 — priceList 4 expected")
    void test5() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                buildUrl("2020-06-16T21:00:00", 35455, 1), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("\"priceList\":4");
    }
}

