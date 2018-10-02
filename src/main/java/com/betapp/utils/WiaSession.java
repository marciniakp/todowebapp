package com.betapp.utils;

import com.betapp.model.Customer;
import org.apache.wicket.Session;
import org.apache.wicket.core.request.ClientInfo;
import org.apache.wicket.request.Request;

public class WiaSession extends Session {

    public static WiaSession get() {
        return (WiaSession) Session.get();
    }

    public WiaSession(Request request) {
        super(request);
    }

    @Override
    public ClientInfo getClientInfo() {
        return null;
    }

    private Customer customer;

    public boolean isAuthenticated() {
        return customer !=null;
    }

    public Customer getPlayer() {
        return customer;
    }

    public void setPlayer(Customer customer) {
        this.customer = customer;
    }

    public void logout() {
        customer = null;
    }
}
