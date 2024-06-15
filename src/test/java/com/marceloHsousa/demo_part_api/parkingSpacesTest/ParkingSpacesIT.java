package com.marceloHsousa.demo_part_api.parkingSpacesTest;

import com.marceloHsousa.demo_part_api.usersTest.JwtAuthentication;
import com.marceloHsousa.demo_part_api.web.dto.parkingSpaceDto.ParkingSpacesDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ParkingSpacesIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void createParkingSpaces_withValidData_returnStatus201(){
        testClient
                .post()
                .uri("api/v1/parkingSpaces")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@email.com", "211111"))
                .bodyValue(new ParkingSpacesDto("1131", "FREE"))
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists(HttpHeaders.LOCATION);
    }

    @Test
    public void createParkingSpaces_codeAlreadyExists_returnStatus409(){
        testClient
                .post()
                .uri("api/v1/parkingSpaces")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@email.com", "211111"))
                .bodyValue(new ParkingSpacesDto("1111", "FREE"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody()
                .jsonPath("status").isEqualTo(409)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/api/v1/parkingSpaces");
    }

    @Test
    public void createParkingSpaces_withInvalidData_returnStatus409(){
        testClient
                .post()
                .uri("api/v1/parkingSpaces")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@email.com", "211111"))
                .bodyValue(new ParkingSpacesDto("", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo(422)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/api/v1/parkingSpaces");

        testClient
                .post()
                .uri("api/v1/parkingSpaces")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@email.com", "211111"))
                .bodyValue(new ParkingSpacesDto("33333", "DELETADA"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo(422)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/api/v1/parkingSpaces");
    }

    @Test
    public void findParkingSpaces_withValidCode_returnStatus200() {
        testClient
                .get()
                .uri("api/v1/parkingSpaces/{code}", "0000")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@email.com", "211111"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("id").isEqualTo(1)
                .jsonPath("code").isEqualTo("0000")
                .jsonPath("status").isEqualTo("FREE");
    }

    @Test
    public void findParkingSpaces_withInvalidCode_returnStatus404() {
        testClient
                .get()
                .uri("api/v1/parkingSpaces/{code}", "5555")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@email.com", "211111"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo(404)
                .jsonPath("method").isEqualTo("GET")
                .jsonPath("path").isEqualTo("/api/v1/parkingSpaces/5555");
    }

    @Test
    public void findParkingSpaces_withIUserNotAllowed_returnStatus403() {
        testClient
                .get()
                .uri("api/v1/parkingSpaces/{code}", "0000")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "julio@email.com", "211111"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .jsonPath("status").isEqualTo(403)
                .jsonPath("method").isEqualTo("GET")
                .jsonPath("path").isEqualTo("/api/v1/parkingSpaces/0000");
    }

    @Test
    public void createParkingSpaces_withIUserNotAllowed_returnStatus403() {
        testClient
                .post()
                .uri("api/v1/parkingSpaces")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "julio@email.com", "211111"))
                .bodyValue(new ParkingSpacesDto("9999", "FREE"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .jsonPath("status").isEqualTo(403)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/api/v1/parkingSpaces");
    }
}
