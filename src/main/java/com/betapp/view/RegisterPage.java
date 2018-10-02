package com.betapp.view;

import com.betapp.model.Customer;
import com.betapp.services.CustomerService;
import com.betapp.utils.WiaSession;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class RegisterPage extends BasePage {
    private static final long serialVersionUID = 1L;

    private Label alertLabel;

    @SpringBean
    CustomerService customerService;

    public RegisterPage() {
        alertLabel = new Label("alertLabel", Model.of(""));
        this.add(alertLabel);
        alertLabel.setVisible(false);

        add(new RegisterPageForm("form"));
    }

    private class RegisterPageForm extends Form<Customer> {

        public RegisterPageForm(String id) {
            super(id);


            TextField<String> login = new TextField<>("login", Model.of(""));
            add(login);
            PasswordTextField login1 = new PasswordTextField("password", Model.of(""));
            add(login1);

            add(new AjaxButton("button", Model.of("Zarejestruj się")) {
                @Override
                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    super.onSubmit(target, form);
                    if(login.getModelObject().length()<4 && login1.getModelObject().length()<6) {
                        alertLabel.setVisible(true);
                        alertLabel.setDefaultModel(Model.of("Login powinien mieć minimum 4 znaki, hasło minimum 6 znaków"));
                        target.add(RegisterPage.this);
                    } else if (customerService.getCustomer(login.getModelObject())!=null) {
                        alertLabel.setVisible(true);
                        alertLabel.setDefaultModel(Model.of("Login jest już zajęty"));
                        target.add(RegisterPage.this);
                    } else {
                        Customer customer = customerService.register(new Customer(login.getModelObject(), login1.getModel().getObject()));
                        WiaSession.get().setPlayer(customer);
                        alertLabel.setVisible(false);
                        target.add(RegisterPage.this);
                        RegisterPage.this.setResponsePage(HomePage.class);
                    }

            }});
        }
    }
}
