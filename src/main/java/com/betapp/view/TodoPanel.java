package com.betapp.view;

import com.betapp.model.TODO;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class TodoPanel extends Panel {
    public TodoPanel(String id, IModel<TODO> model) {
        super(id, model);

        add(new Label("desription", model.getObject().getDescription()));

    }
}
