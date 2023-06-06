package com.rakib.fraud;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/fraud-check")
@RequiredArgsConstructor
@Slf4j
public class FraudCheckController {

    private final FraudCheckService fraudCheckService;

    @GetMapping(path = "{customerID}")
    public FraudCheckResponse isFraud(@PathVariable Integer customerID) {
        boolean isFraudulentCustomer = fraudCheckService.isFraudClientCustomer(customerID);
        log.info("Fraud Check Request for Customer {}" + customerID);
        return new FraudCheckResponse(isFraudulentCustomer);
    }

}
