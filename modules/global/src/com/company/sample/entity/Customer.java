package com.company.sample.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import javax.persistence.UniqueConstraint;
import com.haulmont.cuba.core.entity.annotation.Listeners;

@NamePattern("%s|name")
@Table(name = "SAMPLE_CUSTOMER", uniqueConstraints = {
    @UniqueConstraint(name = "IDX_SAMPLE_CUSTOMER_UNQ_EMAIL", columnNames = {"EMAIL"})
})
@Entity(name = "sample$Customer")
public class Customer extends StandardEntity {
    private static final long serialVersionUID = 2873554598070583275L;

    @Column(name = "NAME", nullable = false)
    protected String name;

    @Column(name = "EMAIL", length = 100)
    protected String email;

    @Column(name = "GRADE")
    protected Integer grade;

    public void setGrade(CustomerGrade grade) {
        this.grade = grade == null ? null : grade.getId();
    }

    public CustomerGrade getGrade() {
        return grade == null ? null : CustomerGrade.fromId(grade);
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }


}