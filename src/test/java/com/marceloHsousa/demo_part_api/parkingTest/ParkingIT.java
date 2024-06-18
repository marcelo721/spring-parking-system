package com.marceloHsousa.demo_part_api.parkingTest;


import com.marceloHsousa.demo_part_api.usersTest.JwtAuthentication;
import com.marceloHsousa.demo_part_api.web.dto.parkingDto.ParkingCreateDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ParkingIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void createChekin_withValidData_returnStatus201(){

        ParkingCreateDto createDto =  ParkingCreateDto.builder()
                .numberPlate("CCC-1111")
                .carModel("hilux")
                .color("red")
                .brand("toyota")
                .clientCpf("64534554044")
                .build();

        testClient
                .post()
                .uri("/api/v1/parkings/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@email.com", "211111"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists(HttpHeaders.LOCATION)
                .expectBody()
                .jsonPath("numberPlate").isEqualTo("CCC-1111")
                .jsonPath("carModel").isEqualTo("hilux")
                .jsonPath("color").isEqualTo("red")
                .jsonPath("brand").isEqualTo("toyota")
                .jsonPath("clientCpf").isEqualTo("64534554044")
                .jsonPath("receipt").exists()
                .jsonPath("parkingSpacesCode").exists()
                .jsonPath("checkInDate").exists();
    }

    @Test
    public void createChekin_withRoleClient_returnStatus403(){

        ParkingCreateDto createDto =  ParkingCreateDto.builder()
                .numberPlate("CCC-1111")
                .carModel("hilux")
                .color("red")
                .brand("toyota")
                .clientCpf("64534554044")
                .build();

        testClient
                .post()
                .uri("/api/v1/parkings/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "julio@email.com", "211111"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .jsonPath("status").isEqualTo("403")
                .jsonPath("path").isEqualTo("/api/v1/parkings/check-in")
                .jsonPath("method").isEqualTo("POST");
    }

    @Test
    public void createChekin_withInvalidData_returnStatus422(){

        ParkingCreateDto createDto =  ParkingCreateDto.builder()
                .numberPlate("")
                .carModel("")
                .color("")
                .brand("")
                .clientCpf("")
                .build();

        testClient
                .post()
                .uri("/api/v1/parkings/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "julio@email.com", "211111"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo("422")
                .jsonPath("path").isEqualTo("/api/v1/parkings/check-in")
                .jsonPath("method").isEqualTo("POST");
    }

    @Test
    public void createChekin_withNonExistentCpf_returnStatus404(){

        ParkingCreateDto createDto =  ParkingCreateDto.builder()
                .numberPlate("CCC-1111")
                .carModel("corolla")
                .color("red")
                .brand("toyota")
                .clientCpf("64534554044")
                .build();


        testClient
                .post()
                .uri("/api/v1/parkings/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@email.com", "211111"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo("404")
                .jsonPath("path").isEqualTo("/api/v1/parkings/check-in")
                .jsonPath("method").isEqualTo("POST");
    }

    @Test
    public void createChekin_withOccupiedParkingSpaces_returnStatus404(){

        ParkingCreateDto createDto =  ParkingCreateDto.builder()
                .numberPlate("CCC-1111")
                .carModel("hilux")
                .color("red")
                .brand("toyota")
                .clientCpf("60220637016")
                .build();


        testClient
                .post()
                .uri("/api/v1/parkings/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@email.com", "211111"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo("404")
                .jsonPath("path").isEqualTo("/api/v1/parkings/check-in")
                .jsonPath("method").isEqualTo("POST");
    }

    @Test
    public void findCheckIn_withRoleAdmin_returnStatus200(){

        testClient
                .get()
                .uri("/api/v1/parkings/check-in/20240617-194026")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@email.com", "211111"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("numberPlate").isEqualTo("CCC-1111")
                .jsonPath("carModel").isEqualTo("hilux")
                .jsonPath("color").isEqualTo("red")
                .jsonPath("brand").isEqualTo("toyota")
                .jsonPath("clientCpf").isEqualTo("64534554044")
                .jsonPath("receipt").isEqualTo("20240617-194026")
                .jsonPath("parkingSpacesCode").exists()
                .jsonPath("checkInDate").exists();
    }

    @Test
    public void findCheckIn_withRoleClient_returnStatus200(){

        testClient
                .get()
                .uri("/api/v1/parkings/check-in/20240618-125943")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "julio@email.com", "211111"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("numberPlate").isEqualTo("AIC-1111")
                .jsonPath("carModel").isEqualTo("d-20")
                .jsonPath("color").isEqualTo("WHITE")
                .jsonPath("brand").isEqualTo("chevrolet")
                .jsonPath("clientCpf").isEqualTo("19904672024")
                .jsonPath("receipt").isEqualTo("20240618-125943")
                .jsonPath("parkingSpacesCode").exists()
                .jsonPath("checkInDate").exists();
    }

    @Test
    public void findCheckIn_withCheckInInvalid_returnStatus404(){

        testClient
                .get()
                .uri("/api/v1/parkings/check-in/20240618-000000")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "julio@email.com", "211111"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo("404")
                .jsonPath("path").isEqualTo("/api/v1/parkings/check-in/20240618-000000")
                .jsonPath("method").isEqualTo("GET");
    }

}
