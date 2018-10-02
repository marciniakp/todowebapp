package com.betapp.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer implements Serializable{

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String password;

    @OneToMany (mappedBy = "customer", fetch = FetchType.EAGER)
    private List<TODO> todos = new ArrayList<>();

    public Customer() {
    }

    public Customer(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<TODO> getTodos() {
        return todos;
    }

    public void setTodos(List<TODO> todos) {
        this.todos = todos;
    }


}
