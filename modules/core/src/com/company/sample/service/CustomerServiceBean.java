package com.company.sample.service;

import com.company.sample.entity.Customer;
import com.company.sample.entity.CustomerGrade;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.global.Metadata;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.UUID;

@Service(CustomerService.NAME)
public class CustomerServiceBean implements CustomerService {

    @Inject
    private Persistence persistence;

    @Inject
    protected Metadata metadata;

    @Override
    public UUID createCustomer(String name, String email, String grade) {
        Customer customer = metadata.create(Customer.class);
        customer.setName(name);
        customer.setEmail(email);
        customer.setGrade(CustomerGrade.valueOf(grade));

        try (Transaction tx = persistence.createTransaction()) {
            persistence.getEntityManager().persist(customer);
            tx.commit();
        }

        return customer.getId();
    }
}