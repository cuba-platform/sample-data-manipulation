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

import java.util.UUID;

public interface CustomerService {
    String NAME = "sample_CustomerService";

    /**
     * Creates a new customer. This method is invoked through REST API.
     * @param name  customer name
     * @param email customer email
     * @param grade string representation of customer grade
     * @return new customer ID
     */
    UUID createCustomer(String name, String email, String grade);

    /**
     * Creates a new customer or updates an existing one with the given name.
     * @param name  customer name
     * @param email customer email
     * @param grade customer grade
     */
    void createOrUpdateCustomer(String name, String email, CustomerGrade grade);

    /**
     * Deletes all customers created by the current user.
     */
    void deleteAll();

    /**
     * Gets a discount value for a customer grade and product type.
     * @param customerGrade customer grade
     * @param productType   product type
     * @return  discount value
     */
    Double getDiscount(CustomerGrade customerGrade, ProductType productType);
}