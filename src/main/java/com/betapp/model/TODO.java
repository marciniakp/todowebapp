package com.betapp.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class TODO implements Serializable{

    @Id
    @GeneratedValue
    private Long id;
    private String description;
    private boolean done = false;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public TODO(String description) {
        this.description = description;
    }

    public TODO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
