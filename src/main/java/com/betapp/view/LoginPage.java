package com.betapp.view;

import com.betapp.model.Customer;
import com.betapp.services.CustomerService;
import com.betapp.utils.WiaSession;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class LoginPage extends BasePage {
    private static final long serialVersionUID = 1L;

    @SpringBean
    CustomerService customerService;

    private Label alertLabel;

    public LoginPage() {
        super();
        alertLabel = new Label("alertLabel", Model.of(""));
        add(alertLabel);
        alertLabel.setVisible(false);

        add(new LoginPageForm("form"));
    }
    private class LoginPageForm extends Form<Customer> {

        public LoginPageForm(String id) {
            super(id);


            TextField<String> login = new TextField<>("login", Model.of(""));
            add(login);
            PasswordTextField login1 = new PasswordTextField("password", Model.of(""));
            add(login1);

            add(new AjaxButton("button", Model.of("Zaloguj się")) {
                @Override
                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    super.onSubmit(target, form);
                    Customer customer = customerService.getCustomer(login.getModelObject());
                    if (customer != null && customer.getPassword().equals(login1.getModelObject())) {
                        WiaSession.get().setPlayer(customer);
                        alertLabel.setVisible(false);
                        target.add(this);
                        LoginPage.this.setResponsePage(HomePage.class);
                    } else {
                        alertLabel.setVisible(true);
                        alertLabel.setDefaultModel(Model.of("Podano nieprawidłowy login lub hasło"));
                        alertLabel.setOutputMarkupId(true);
                        target.add(LoginPage.this);
                    }

                }
            }.add(new AjaxEventBehavior("onclick") {
                @Override
                protected void onEvent(AjaxRequestTarget target) {
                    if(login.getModelObject().length()<1 || login.getModelObject().length()<1) {
                        alertLabel.setDefaultModel(Model.of("Pola nie mogą być puste"));
                    }
                    alertLabel.setVisible(true);
                }
            }));
        }

    }
}
