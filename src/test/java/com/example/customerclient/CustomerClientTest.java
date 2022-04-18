package com.example.customerclient;

import org.assertj.core.api.BDDAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

@SpringBootTest(classes = CustomerClient.class)
@RunWith(SpringRunner.class)
@AutoConfigureStubRunner(ids = "com.example:customer-service:+:8081", stubsMode = StubRunnerProperties.StubsMode.LOCAL)
@AutoConfigureJson
public class CustomerClientTest {

    @Autowired
    private CustomerClient customerClient;

    @Test
    public void clientShouldReturnAllCustomers() {
        Collection<Customer> allCustomers = this.customerClient.getAllCustomers();
        BDDAssertions.then(allCustomers).size().isEqualTo(2);
        BDDAssertions.then(allCustomers.iterator().next().getId()).isEqualTo(1L);
        BDDAssertions.then(allCustomers.iterator().next().getName()).isEqualTo("Bob");
    }

}