package com.example.customerclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.HttpHeader;
import org.assertj.core.api.BDDAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import wiremock.org.apache.http.HttpHeaders;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

@SpringBootTest(classes = CustomerClient.class)
@RunWith(SpringRunner.class)
@AutoConfigureWireMock(port = 8081)
@AutoConfigureJson
public class CustomerClientTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerClient customerClient;

    @Test
    public void clientShouldReturnAllCustomers() throws Exception {
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/customers"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(jsonForCustomers(
                                new Customer(1L, "Bob"),
                                new Customer(2L, "Jane")))
                ));

        BDDAssertions.then(this.customerClient.getAllCustomers()).size().isEqualTo(2);
    }

    private String jsonForCustomers(Customer...customers) throws Exception {
        List<Customer> customersList = Arrays.asList(customers);
        return this.objectMapper.writeValueAsString(customersList);
    }

}