package com.company.sample.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import javax.persistence.UniqueConstraint;

@NamePattern("%s - %s|customerGrade,productType")
@Table(name = "SAMPLE_DISCOUNT", uniqueConstraints = {
    @UniqueConstraint(name = "IDX_SAMPLE_DISCOUNT_UNQ", columnNames = {"CUSTOMER_GRADE", "PRODUCT_TYPE", "DISCOUNT"})
})
@Entity(name = "sample$Discount")
public class Discount extends StandardEntity {
    private static final long serialVersionUID = -6968067880426095505L;

    @Column(name = "CUSTOMER_GRADE")
    protected Integer customerGrade;

    @Column(name = "PRODUCT_TYPE")
    protected String productType;

    @Column(name = "DISCOUNT")
    protected Double discount;

    public void setCustomerGrade(CustomerGrade customerGrade) {
        this.customerGrade = customerGrade == null ? null : customerGrade.getId();
    }

    public CustomerGrade getCustomerGrade() {
        return customerGrade == null ? null : CustomerGrade.fromId(customerGrade);
    }

    public void setProductType(ProductType productType) {
        this.productType = productType == null ? null : productType.getId();
    }

    public ProductType getProductType() {
        return productType == null ? null : ProductType.fromId(productType);
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getDiscount() {
        return discount;
    }


}