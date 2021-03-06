package com.example.customerclient;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

@Service
public class CustomerClient {
    private final RestTemplate restTemplate;

    public CustomerClient() {
        this.restTemplate = new RestTemplate();
    }

    public Collection<Customer> getAllCustomers() {
        ParameterizedTypeReference<Collection<Customer>> ptr = new ParameterizedTypeReference<>() { };
        ResponseEntity<Collection<Customer>> responseEntity = restTemplate.exchange("http://localhost:8081/customers",
                HttpMethod.GET, null, ptr);
        return responseEntity.getBody();
    }
}
