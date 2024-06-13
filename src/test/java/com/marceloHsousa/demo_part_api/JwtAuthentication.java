package com.marceloHsousa.demo_part_api;

import com.marceloHsousa.demo_part_api.jwt.JwtToken;
import com.marceloHsousa.demo_part_api.web.dto.UserLonginDto;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.function.Consumer;

public class JwtAuthentication {

    public static Consumer<HttpHeaders> getHeaderAuthorization(WebTestClient client, String username, String password){

        String token = client
                .post()
                .uri("/api/v1/auth")
                .bodyValue(new UserLonginDto(username, password))
                .exchange()
                .expectStatus().isOk()
                .expectBody(JwtToken.class)
                .returnResult().getResponseBody().getToken();

        return headers -> headers.add(HttpHeaders.AUTHORIZATION, "Bearer "+ token);
    }
}
