package com.lapshovi.HaulmontTestTask.views;

import com.lapshovi.HaulmontTestTask.model.Credit;
import com.lapshovi.HaulmontTestTask.service.CreditService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class CreditView extends VerticalLayout implements KeyNotifier {

    private final CreditService creditService;

    private Credit credit;
    private ChangeHandler changeHandler;


    TextField nameOfCredit = new TextField("Название кредита");
    TextField limitOfCredit = new TextField("Лимит по кредиту");
    TextField percentOfCredit = new TextField("Процентная ставка");

    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());

    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<Credit> binder = new Binder<>(Credit.class);

    public interface ChangeHandler {
        void onChange();
    }

    @Autowired
    public CreditView(CreditService creditService) {
        this.creditService = creditService;
        add(nameOfCredit, limitOfCredit, percentOfCredit, actions);

        binder.forField(nameOfCredit)
                .withNullRepresentation("")
                .withValidator(name -> name.length() > 0,
                        "Поле должно быть заполнено")
                .bind(Credit::getNameOfCredit, Credit::setNameOfCredit);

        binder.forField(limitOfCredit)
                .withNullRepresentation("")
                .withValidator(name -> name.length() > 0,
                        "Поле должно быть заполнено")
                .withConverter(new StringToLongConverter("Поле должно быть числом"))
                .bind(Credit::getLimitOfCredit,
                        Credit::setLimitOfCredit);
        binder.forField(percentOfCredit)
                .withNullRepresentation("")
                .withValidator(name -> name.length() > 0,
                        "Поле должно быть заполнено")
                .withConverter(new StringToDoubleConverter("Поле должно быть числом"))
                .bind(Credit::getPercentOfCredit,
                        Credit::setPercentOfCredit);
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
        creditService.deleteCredit(credit);
        changeHandler.onChange();
    }

    void save() {
        creditService.saveCredit(credit);
        changeHandler.onChange();
    }

    public final void editCredit(Credit newCredit) {
        if (newCredit == null) {
            setVisible(false);
            return;
        }
        if (newCredit.getId() != null) {

            if (newCredit.getId() == null)
                credit = creditService.findById(newCredit.getId());
            else credit = newCredit;
        } else {
            credit = newCredit;
        }
        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        binder.setBean(credit);
        setVisible(true);
        nameOfCredit.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

}


