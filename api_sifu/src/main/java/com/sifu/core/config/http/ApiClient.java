package com.sifu.core.config.http;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;


public class ApiClient {

    private RestClient restClient;

    public ApiClient(String baseUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl) // URL base para todas las solicitudes
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();
    }

    public <T> ResponseEntity<T> makeRequest(
            HttpMethod method,
            String endpoint,
            Object requestBody,
            Class<T> responseType,
            String... customHeaders) {

        try {
            RestClient.RequestBodySpec requestSpec = restClient.method(method)
                    .uri(endpoint)
                    .headers(headers -> {
                        if (customHeaders != null && customHeaders.length > 0) {
                            for (int i = 0; i < customHeaders.length; i += 2) {
                                headers.add(customHeaders[i], customHeaders[i + 1]);
                            }
                        }
                    });

            if (requestBody != null) {
                requestSpec.body(requestBody);
            }

            T responseBody = requestSpec
                    .retrieve()
                    .body(responseType);

            return ResponseEntity.ok(responseBody);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).build();
        }
    }
}
