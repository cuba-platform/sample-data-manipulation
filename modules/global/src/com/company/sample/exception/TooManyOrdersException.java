package com.company.sample.exception;

import com.haulmont.cuba.core.global.SupportedByClient;

import java.util.Date;

/**
 * Exception thrown on attempt to save an order if there are more than 2 orders for a customer in a day.
 */
@SupportedByClient
public class TooManyOrdersException extends RuntimeException {

    private final String name;

    public TooManyOrdersException(String customerName, Date date) {
        super(String.format("customerName=%s, date=%s", customerName, date));
        this.name = customerName;
    }

    public String getName() {
        return name;
    }
}
