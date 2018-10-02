package com.betapp.view;

import com.betapp.model.TODO;
import com.betapp.services.CustomerService;
import com.betapp.services.TODOService;
import com.betapp.utils.WiaSession;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends BasePage {
	private static final long serialVersionUID = 1L;

	@SpringBean
	TODOService todoService;

	@SpringBean
	CustomerService customerService;

	private Label alertLabel;
	private Label infoLabel;
	private ListView notDone;
	private ListView done;
	private IModel<ArrayList<TODO>> notDoneTodos = new Model<>(new ArrayList<>());
	private IModel<ArrayList<TODO>> doneTodos = new Model<>(new ArrayList<>());

	public HomePage() {
		super();

		Model<String> infoLabelModel = Model.of(WiaSession.get().isAuthenticated() ?
				"Kliknij na zadanie, aby zmienić jego status." +
						"Zadanie czerwone - niewykonane, zadanie zielone - wykonane." :
				"Musisz się zalogować, aby korzystać z portalu");
		infoLabel = new Label("infoLabel", infoLabelModel);
		add(infoLabel);
		alertLabel = new Label("alertLabel", Model.of("Zadanie musi składać się z minimum 5 liter"));
		alertLabel.setVisible(false);
		alertLabel.setOutputMarkupId(true);
		add(alertLabel);

		AddTodoForm form = new AddTodoForm("form");
		add(form);

		getTodos();

		notDone = new ListView("notDone", notDoneTodos) {
			@Override
			protected void populateItem(ListItem item) {
				item.add(new TodoPanel("notDoneTodos", item.getModel()).add(new AjaxEventBehavior("onclick") {
					@Override
					protected void onEvent(AjaxRequestTarget target) {
						TODO todo = (TODO) item.getModel().getObject();
						todo.setDone(true);
						todoService.add(todo);
						notDoneTodos.getObject().remove(todo);
						doneTodos.getObject().add(todo);
						target.add(HomePage.this);
					}
				}));
			}
		};
		done = new ListView("done", doneTodos) {
			@Override
			protected void populateItem(ListItem item) {
				item.add(new TodoPanel("doneTodos", item.getModel()).add(new AjaxEventBehavior("onclick") {
					@Override
					protected void onEvent(AjaxRequestTarget target) {
						TODO todo = (TODO) item.getModel().getObject();
						todo.setDone(false);
						todoService.add(todo);
						doneTodos.getObject().remove(todo);
						notDoneTodos.getObject().add(todo);
						//todoService.getTODO(todo.getId()).setDone(true);
						target.add(HomePage.this);
					}
				}));
			}
		};

		add(notDone);
		add(done);

		if(!WiaSession.get().isAuthenticated()) {

			form.setEnabled(false);
		} else {
			form.setEnabled(true);
		}
		add(new Label("firstRecordInfo","Dodaj pierwsze zadanie!").setVisible(doneTodos.getObject().isEmpty() && notDoneTodos.getObject().isEmpty()));


	}

	private class AddTodoForm extends Form {

		public AddTodoForm(String id) {
			super(id);

			TextField<String> input = new TextField<>("input", Model.of(""));
			add(input);

			add(new AjaxButton("submit", Model.of("Dodaj")) {
				@Override
				protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
					super.onSubmit(target, form);
					if(input.getConvertedInput().length()<4) {
						alertLabel.setVisible(true);
					} else {
						alertLabel.setVisible(false);
						TODO todo = new TODO(input.getConvertedInput());
						todo.setCustomer(WiaSession.get().getPlayer());
						WiaSession.get().getPlayer().getTodos().add(todo);
						TODO add = todoService.add(todo);
						notDoneTodos.getObject().add(add);
						input.setDefaultModel(Model.of(""));
					}
					target.add(HomePage.this);
				}
			});
		}
	}

	private void getTodos() {
		customerService.getDoneTodos().forEach(t->doneTodos.getObject().add(t));
		customerService.getNotDoneTodos().forEach(t->notDoneTodos.getObject().add(t));
	}

}
