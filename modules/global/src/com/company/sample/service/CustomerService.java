package com.company.sample.service;

import java.util.UUID;

public interface CustomerService {
    String NAME = "sample_CustomerService";

    UUID createCustomer(String name, String email, String grade);
}