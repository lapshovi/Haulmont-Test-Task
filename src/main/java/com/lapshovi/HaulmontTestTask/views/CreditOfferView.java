package com.lapshovi.HaulmontTestTask.views;

import com.lapshovi.HaulmontTestTask.model.Client;
import com.lapshovi.HaulmontTestTask.model.Credit;
import com.lapshovi.HaulmontTestTask.model.CreditOffer;
import com.lapshovi.HaulmontTestTask.service.ClientService;
import com.lapshovi.HaulmontTestTask.service.CreditOfferService;
import com.lapshovi.HaulmontTestTask.service.CreditService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class CreditOfferView extends VerticalLayout implements KeyNotifier {

    private final CreditOfferService creditOfferService;
    private final ClientService clientService;
    private final CreditService creditService;

    private CreditOffer creditOffer;
    private ChangeHandler changeHandler;

    ComboBox<Client> client = new ComboBox<>("Клиент");
    ComboBox<Credit> credit = new ComboBox<>("Название кредита");
    TextField amountOfCredit = new TextField("Сумма кредита");
    TextField creditPeriodInMonths = new TextField("Срок кредита, месяцы");

    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());

    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<CreditOffer> binder = new Binder<>(CreditOffer.class);

    public interface ChangeHandler {
        void onChange();
    }

    @Autowired
    public CreditOfferView(CreditOfferService creditOfferService, ClientService clientService, CreditService creditService) {
        this.creditOfferService = creditOfferService;
        this.clientService = clientService;
        this.creditService = creditService;

        add(client, credit, amountOfCredit, creditPeriodInMonths, actions);
        client.setItems(clientService.findAll());
        client.setItemLabelGenerator(Client::getName);
        credit.setItems(creditService.findAll());
        credit.setItemLabelGenerator(Credit::getNameOfCredit);

        binder.forField(client).bind(CreditOffer::getClient, CreditOffer::setClient);
        binder.forField(credit).bind(CreditOffer::getCredit, CreditOffer::setCredit);
        binder.forField(amountOfCredit)
                .withNullRepresentation("")
                .withConverter(new StringToLongConverter("Поле должно быть числом"))
                .withValidator(e -> {
                    if (e == null)
                        return false;
                    else
                        return e <= creditOffer.getCredit().getLimitOfCredit();
                }, "Сумма кредита должна быть меньше лимита")
                .bind(CreditOffer::getAmountOfCredit, CreditOffer::setAmountOfCredit);
        binder.forField(creditPeriodInMonths)
                .withNullRepresentation("")
                .withConverter(new StringToIntegerConverter("Поле должно быть числом"))
                .bind(CreditOffer::getCreditPeriodInMonths, CreditOffer::setCreditPeriodInMonths);

        setSpacing(false);
        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");
        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> setVisible(false));

        setVisible(false);
    }

    void delete() {
        creditOfferService.deleteCreditoffer(creditOffer);
        changeHandler.onChange();
    }

    void save() {
        creditOfferService.saveCreditOffer(creditOffer);
        changeHandler.onChange();
    }

    public final void editCreditOffer(CreditOffer newCreditOffer) {
        if (newCreditOffer == null) {
            setVisible(false);
            return;
        }
        if (newCreditOffer.getId() != null) {
            if (newCreditOffer.getId() == null) {
                creditOffer = creditOfferService.findById(newCreditOffer.getId());
            } else creditOffer = newCreditOffer;
        } else {
            creditOffer = newCreditOffer;
        }
        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        client.setItems(clientService.findAll());
        credit.setItems(creditService.findAll());
        binder.setBean(creditOffer);
        setVisible(true);
        amountOfCredit.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

}

