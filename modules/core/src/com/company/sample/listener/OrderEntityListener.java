package com.company.sample.listener;

import com.company.sample.exception.TooManyOrdersException;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Query;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;
import com.haulmont.cuba.core.listener.BeforeUpdateEntityListener;
import org.springframework.stereotype.Component;
import com.company.sample.entity.Order;

/**
 * Ensures there are not more than 2 orders for a customer in a day.
 */
@Component("sample_OrderEntityListener")
public class OrderEntityListener implements BeforeInsertEntityListener<Order>, BeforeUpdateEntityListener<Order> {

    @Override
    public void onBeforeInsert(Order entity, EntityManager entityManager) {
        checkConditions(entity, entityManager);
    }

    @Override
    public void onBeforeUpdate(Order entity, EntityManager entityManager) {
        checkConditions(entity, entityManager);
    }

    private void checkConditions(Order order, EntityManager entityManager) {
        Query query = entityManager.createQuery(
                "select count(e) from sample$Order e where e.date = ?1 and e.customer.id = ?2 and e.id <> ?3");
        query.setParameter(1, order.getDate());
        query.setParameter(2, order.getCustomer());
        query.setParameter(3, order.getId());
        Long count = (Long) query.getSingleResult();
        if (count >= 2)
            throw new TooManyOrdersException(order.getCustomer().getName(), order.getDate());
    }
}