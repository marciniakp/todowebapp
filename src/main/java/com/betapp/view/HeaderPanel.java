package com.betapp.view;

import com.betapp.utils.WiaSession;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.panel.Panel;

public class HeaderPanel extends Panel {
    public HeaderPanel(String id) {
        super(id);

        add(new AjaxLink("mainPage") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                setResponsePage(HomePage.class);
            }
        });
        add(new AjaxLink("login") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                setResponsePage(LoginPage.class);
            }
        }.setVisible(!WiaSession.get().isAuthenticated()));
        add(new AjaxLink("register") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                setResponsePage(RegisterPage.class);
            }
        }.setVisible(!WiaSession.get().isAuthenticated()));
        add(new AjaxLink("logout") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                WiaSession.get().logout();
                setResponsePage(HomePage.class);
            }
        }.setVisible(WiaSession.get().isAuthenticated()));

    }
}
