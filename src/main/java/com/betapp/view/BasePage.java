package com.betapp.view;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebPage;

public abstract class BasePage extends WebPage {
    public BasePage() {
        super();

        add(new HeaderPanel("header"));

    }
}
