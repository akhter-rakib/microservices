package com.rakib.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;

    public void registerCustomer(CustomerRegistrationRequest customerRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRequest.firstName())
                .lastName(customerRequest.lastName())
                .email(customerRequest.email())
                .build();
        customerRepository.saveAndFlush(customer);
        /*Check fraudster*/
        FraudCheckResponse fraudCheckResponse = restTemplate
                .getForObject("http://localhost:8081/api/v1/fraud-check/{customerId}",
                        FraudCheckResponse.class,
                        customer.getId());
        assert fraudCheckResponse != null;
        if (fraudCheckResponse.isFraud()) {
            throw new IllegalStateException("fraudster");
        }
    }
}
