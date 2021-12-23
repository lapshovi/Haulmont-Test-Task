package com.lapshovi.HaulmontTestTask.views;

import com.lapshovi.HaulmontTestTask.model.Client;
import com.lapshovi.HaulmontTestTask.service.ClientService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class ClientView extends VerticalLayout implements KeyNotifier {

    private final ClientService clientService;

    private Client client;
    private ChangeHandler changeHandler;


    TextField name = new TextField("ФИО");
    TextField phoneNumber = new TextField("Номер телефона");
    TextField email = new TextField("Email");
    TextField passportNumber = new TextField("Номер паспорта");

    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());

    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<Client> binder = new Binder<>(Client.class);

    public interface ChangeHandler {
        void onChange();
    }

    @Autowired
    public ClientView(ClientService clientService) {
        this.clientService = clientService;
        add(name, phoneNumber, email, passportNumber, actions);
        binder.forField(name)
                .withValidator(name -> name.length() > 0,
                        "Поле должно быть заполнено")
                .withNullRepresentation("")
                .bind(Client::getName, Client::setName);
        binder.forField(phoneNumber)
                .withNullRepresentation("")
                .withConverter(new StringToLongConverter("Поле должно быть числом"))
                .bind(Client::getPhoneNumber, Client::setPhoneNumber);
        binder.forField(passportNumber)
                .withValidator(name -> name.length() > 0,
                        "Поле должно быть заполнено")
                .withNullRepresentation("")
                .withConverter(new StringToLongConverter("Поле должно быть числом"))
                .bind(Client::getPassportNumber, Client::setPassportNumber);
        binder.forField(email)
                .withNullRepresentation("")
                .withValidator(new EmailValidator("Введите email"))
                .bind(Client::getEmail, Client::setEmail);

        setSpacing(true);
        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");
        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> setVisible(false));
        setVisible(false);
    }

    void delete() {
        clientService.deleteClient(client);
        changeHandler.onChange();
    }

    void save() {
        clientService.saveClient(client);
        changeHandler.onChange();
    }

    public final void editClient(Client newClient) {
        if (newClient == null) {
            setVisible(false);
            return;
        }
        if (newClient.getId() != null) {
            if (newClient.getId() == null)
                client = clientService.findById(newClient.getId());
            else client = newClient;
        } else {
            client = newClient;
        }
        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        binder.setBean(client);
        setVisible(true);
        name.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }
}
