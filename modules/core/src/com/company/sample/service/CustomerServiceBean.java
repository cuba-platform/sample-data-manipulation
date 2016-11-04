/*
 * Copyright (c) 2016 Haulmont
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.company.sample.service;

import com.company.sample.entity.Customer;
import com.company.sample.entity.CustomerGrade;
import com.company.sample.entity.ProductType;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Query;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.UserSessionSource;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@Service(CustomerService.NAME)
public class CustomerServiceBean implements CustomerService {

    @Inject
    private Persistence persistence;

    @Inject
    private Metadata metadata;

    @Inject
    private UserSessionSource userSessionSource;

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

    @Override
    public void createOrUpdateCustomer(String name, String email, CustomerGrade grade) {
        try (Transaction tx = persistence.createTransaction()) {
            // Search for existing customers with the given name
            TypedQuery<Customer> query = persistence.getEntityManager().createQuery(
                    "select c from sample$Customer c where c.name = :name", Customer.class);
            query.setParameter("name", name);
            List<Customer> customers = query.getResultList();
            if (customers.size() == 0) {
                // no customer with the given name, creating it
                Customer customer = metadata.create(Customer.class);
                customer.setName(name);
                customer.setEmail(email);
                customer.setGrade(grade);
                // persist the new instance
                persistence.getEntityManager().persist(customer);
            } else if (customers.size() == 1) {
                // a customer found, updating it
                Customer customer = customers.get(0);
                customer.setEmail(email);
                customer.setGrade(grade);
                // the loaded customer is in Managed state and will be saved to the database on transaction commit
            } else {
                // more than one customer found, raising an error
                throw new IllegalStateException("More than one customer with name " + name);
            }
            tx.commit();
        }
    }

    @Override
    public void deleteAll() {
        try (Transaction tx = persistence.createTransaction()) {
            Query query = persistence.getEntityManager().createQuery(
                    "delete from sample$Customer c where c.createdBy = :createdBy");
            query.setParameter("createdBy", userSessionSource.getUserSession().getUser().getLogin());
            query.executeUpdate();
            tx.commit();
        }
    }

    @Override
    public Double getDiscount(CustomerGrade customerGrade, ProductType productType) {
        Double value;
        try (Transaction tx = persistence.createTransaction()) {
            Query query = persistence.getEntityManager().createQuery(
                    "select d.discount from sample$Discount d where d.customerGrade = :grade and d.productType = :type");
            query.setParameter("grade", customerGrade);
            query.setParameter("type", productType);
            value = (Double) query.getFirstResult();
            tx.commit();
        }
        if (value == null)
            value = 0.0;
        return value;
    }
}