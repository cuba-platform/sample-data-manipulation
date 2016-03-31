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

package com.company.sample.gui.customer;

import com.company.sample.entity.Customer;
import com.company.sample.entity.CustomerGrade;
import com.company.sample.service.CustomerService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.CollectionDatasource;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CustomerBrowse extends AbstractLookup {

    @Inject
    private CollectionDatasource<Customer, UUID> customersDs;

    @Inject
    private HBoxLayout fieldsBox;
    @Inject
    private TextField nameField;
    @Inject
    private TextField emailField;
    @Inject
    private LookupField gradeField;

    @Inject
    private CustomerService customerService;

    @Inject
    private DataManager dataManager;
    @Inject
    private Metadata metadata;

    @Override
    public void init(Map<String, Object> params) {
        fieldsBox.setVisible(false);
        gradeField.setOptionsList(Arrays.asList(CustomerGrade.values()));
    }

    public void onCreateBtnClick(Component source) {
        fieldsBox.setVisible(true);
    }

    public void onCreateInService(Component source) {
        fieldsBox.setVisible(false);
        String name = nameField.getValue();
        String email = emailField.getValue();
        CustomerGrade grade = gradeField.getValue();

        customerService.createOrUpdateCustomer(name, email, grade);

        customersDs.refresh();
    }

    public void onCreateInController(Component source) {
        fieldsBox.setVisible(false);
        String name = nameField.getValue();
        String email = emailField.getValue();
        CustomerGrade grade = gradeField.getValue();

        // create a LoadContext
        LoadContext<Customer> loadContext = LoadContext.create(Customer.class)
                .setQuery(LoadContext.createQuery("select c from sample$Customer c where c.name = :name")
                        .setParameter("name", name))
                .setView(View.LOCAL);
        // load list using DataManager
        List<Customer> customers = dataManager.loadList(loadContext);

        if (customers.size() == 0) {
            // no customer with the given name, creating it
            Customer customer = metadata.create(Customer.class);
            customer.setName(name);
            customer.setEmail(email);
            customer.setGrade(grade);
            // persist the new instance
            dataManager.commit(customer);
        } else if (customers.size() == 1) {
            // a customer found, updating it
            Customer customer = customers.get(0);
            customer.setEmail(email);
            customer.setGrade(grade);
            // update the customer
            dataManager.commit(customer);
        } else {
            // more than one customer found, raising an error
            throw new IllegalStateException("More than one customer with name " + name);
        }

        customersDs.refresh();
    }

    public void onDeleteAllBtnClick(Component source) {
        showOptionDialog(
                "Warning",
                "All records created by you will be deleted. Continue?",
                MessageType.CONFIRMATION,
                new Action[] {
                        new DialogAction(DialogAction.Type.YES) {
                            @Override
                            public void actionPerform(Component component) {
                                customerService.deleteAll();
                                customersDs.refresh();
                            }
                        },
                        new DialogAction(DialogAction.Type.NO)
                }
        );
    }

    public void onCancelBtnClick(Component source) {
        fieldsBox.setVisible(false);
    }
}