package com.lapshovi.HaulmontTestTask;

import com.lapshovi.HaulmontTestTask.model.Client;
import com.lapshovi.HaulmontTestTask.model.Credit;
import com.lapshovi.HaulmontTestTask.model.CreditOffer;
import com.lapshovi.HaulmontTestTask.model.PaymentSchedule;
import com.lapshovi.HaulmontTestTask.service.ClientService;
import com.lapshovi.HaulmontTestTask.service.CreditOfferService;
import com.lapshovi.HaulmontTestTask.service.CreditService;
import com.lapshovi.HaulmontTestTask.service.PaymentScheduleService;
import com.lapshovi.HaulmontTestTask.views.ClientView;
import com.lapshovi.HaulmontTestTask.views.CreditOfferView;
import com.lapshovi.HaulmontTestTask.views.CreditView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

@Route("")
public class MainUI extends VerticalLayout {

    private final ClientService clientService;
    private final CreditService creditService;
    private final CreditOfferService creditOfferService;
    private final PaymentScheduleService paymentScheduleService;

    private final ClientView clientView;
    private final CreditView creditView;
    private final CreditOfferView creditOfferView;

    private CreditOffer creditOffer;

    private final TextField filter;

    private final Grid<Client> gridOfClient;
    private final Grid<Credit> gridOfCredit;
    private final Grid<CreditOffer> gridOfCreditOffer;
    private final Grid<PaymentSchedule> gridOfPaymentSchedule;

    private final Button addNewClient;
    private final Button addNewCredit;
    private final Button addNewCreditOffer;

    private final Tab clientTab;
    private final Tab creditTab;
    private final Tab creditOfferTab;
    private final Tabs tabs;

    H1 graph = new H1("График платежей");
    H1 graphPercentAmount = new H1("Итоговая сумма процентов: ");

    private final VerticalLayout content;
    private double percentFinalAmountRound;

    @Autowired
    public MainUI(ClientService clientService, ClientView clientView, CreditService creditService, CreditView creditView,
                  CreditOfferService creditOfferService, CreditOfferView creditOfferView,
                  PaymentScheduleService paymentScheduleService) {
        this.clientService = clientService;
        this.creditService = creditService;
        this.creditOfferService = creditOfferService;
        this.paymentScheduleService = paymentScheduleService;
        this.clientView = clientView;
        this.creditView = creditView;
        this.creditOfferView = creditOfferView;

        this.gridOfClient = new Grid<>();
        this.gridOfCredit = new Grid<>();
        this.gridOfCreditOffer = new Grid<>();
        this.gridOfPaymentSchedule = new Grid<>();

        this.filter = new TextField();
        this.addNewClient = new Button("Новый клиент", VaadinIcon.PLUS.create());
        this.addNewCredit = new Button("Новый кредит", VaadinIcon.PLUS.create());
        this.addNewCreditOffer = new Button("Новое кредитное предложение", VaadinIcon.PLUS.create());

        clientTab = new Tab("Клиенты");
        creditTab = new Tab("Кредиты");
        creditOfferTab = new Tab("Кредитное предложение");

        tabs = new Tabs(clientTab, creditTab, creditOfferTab);
        tabs.addSelectedChangeListener(event ->
                setContent(event.getSelectedTab())
        );
        content = new VerticalLayout();
        content.setSpacing(false);
        setContent(tabs.getSelectedTab());
        add(tabs, content);
    }

    private VerticalLayout createClientView() {
        gridOfClient.setHeight("300px");
        gridOfClient.removeAllColumns();
        gridOfClient.addColumn(Client::getId).setHeader("Id");
        gridOfClient.addColumn(Client::getName).setHeader("ФИО");
        gridOfClient.addColumn(Client::getPhoneNumber).setHeader("Номер телефона");
        gridOfClient.addColumn(Client::getEmail).setHeader("Email");
        gridOfClient.addColumn(Client::getPassportNumber).setHeader("Номер паспорта");
        final VerticalLayout verticalLayout = new VerticalLayout();
        HorizontalLayout actions = new HorizontalLayout(addNewClient);
        verticalLayout.add(filter, actions, gridOfClient, clientView);
        add(verticalLayout);
        filter.setPlaceholder("Фильтр по ФИО");

        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> listClients(e.getValue()));

        gridOfClient.asSingleSelect().addValueChangeListener(e -> {
            clientView.editClient(e.getValue());
        });

        addNewClient.addClickListener(e -> clientView.editClient(new Client()));

        clientView.setChangeHandler(() -> {
            clientView.setVisible(false);
            listClients(filter.getValue());
        });

        listClients(null);
        return verticalLayout;

    }

    void listClients(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            gridOfClient.setItems(clientService.findAll());
        } else gridOfClient.setItems(clientService.findByName(filterText));

    }

    private VerticalLayout createCreditView() {
        gridOfCredit.setHeight("300px");
        gridOfCredit.removeAllColumns();
        gridOfCredit.addColumn(Credit::getNameOfCredit).setHeader("Название кредита");
        gridOfCredit.addColumn(Credit::getLimitOfCredit).setHeader("Лимит по кредиту");
        gridOfCredit.addColumn(Credit::getPercentOfCredit).setHeader("Процентная ставка");
        VerticalLayout verticalLayout = new VerticalLayout();
        HorizontalLayout actions = new HorizontalLayout(addNewCredit);
        verticalLayout.add(filter, actions, gridOfCredit, creditView);
        add(verticalLayout);

        filter.setPlaceholder("Фильтр по названию");

        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> listCredits(e.getValue()));

        gridOfCredit.asSingleSelect().addValueChangeListener(e -> {
            creditView.editCredit(e.getValue());
        });

        addNewCredit.addClickListener(e -> creditView.editCredit(new Credit()));

        creditView.setChangeHandler(() -> {
            creditView.setVisible(false);
            listCredits(filter.getValue());
        });
        listCredits(null);
        return verticalLayout;
    }

    void listCredits(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            gridOfCredit.setItems(creditService.findAll());
        } else gridOfCredit.setItems(creditService.findByName(filterText));
    }

    private VerticalLayout createCreditOfferView() {
        gridOfCreditOffer.setHeight("300px");
        gridOfCreditOffer.removeAllColumns();
        gridOfCreditOffer.addColumn(CreditOffer::getClient).setHeader("Клиент");
        gridOfCreditOffer.addColumn(CreditOffer::getCredit).setHeader("Название кредита");
        gridOfCreditOffer.addColumn(CreditOffer::getAmountOfCredit).setHeader("Сумма кредита");
        gridOfCreditOffer.addColumn(CreditOffer::getCreditPeriodInMonths).setHeader("Срок кредита, месяцы");

        VerticalLayout verticalLayout = new VerticalLayout();
        graph.setVisible(false);
        graphPercentAmount.setVisible(false);

        HorizontalLayout actions = new HorizontalLayout(addNewCreditOffer);
        verticalLayout.add(actions, gridOfCreditOffer, creditOfferView, graph, gridOfPaymentSchedule, graphPercentAmount);

        add(verticalLayout);

        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> listCreditOffers(e.getValue()));

        gridOfCreditOffer.asSingleSelect().addValueChangeListener(e -> {
            creditOfferView.editCreditOffer(e.getValue());
            creditOffer = e.getValue();
            if (creditOffer != null) {
                calculation();
                createPaymentSchedule();
                gridOfPaymentSchedule.setVisible(true);
                graph.setVisible(true);
                graphPercentAmount.removeAll();
                graphPercentAmount.setVisible(true);
                graphPercentAmount.add("Итоговая сумма процентов: " + percentFinalAmountRound);
            }
        });

        addNewCreditOffer.addClickListener(e -> creditOfferView.editCreditOffer(new CreditOffer()));

        creditOfferView.setChangeHandler(() -> {
            creditOfferView.setVisible(false);
            gridOfPaymentSchedule.setVisible(false);
            graph.setVisible(false);
            graphPercentAmount.setVisible(false);
            listCreditOffers(filter.getValue());
        });

        listCreditOffers(null);

        gridOfCreditOffer.setItems(creditOfferService.findAll());

        return verticalLayout;

    }

    void listCreditOffers(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            gridOfCreditOffer.setItems(creditOfferService.findAll());
        }
    }

    private void createPaymentSchedule() {

        gridOfPaymentSchedule.setVisible(false);
        gridOfPaymentSchedule.setHeight("300px");
        gridOfPaymentSchedule.removeAllColumns();
        gridOfPaymentSchedule.addColumn(PaymentSchedule::getPaymentDate).setHeader("Дата платежа");
        gridOfPaymentSchedule.addColumn(PaymentSchedule::getPaymentAmount).setHeader("Сумма платежа");
        gridOfPaymentSchedule.addColumn(PaymentSchedule::getBodyAmount).setHeader("Тело кредита");
        gridOfPaymentSchedule.addColumn(PaymentSchedule::getPercentAmount).setHeader("Проценты");
        gridOfPaymentSchedule.addColumn(PaymentSchedule::getRemains).setHeader("Остаток");
        gridOfPaymentSchedule.setItems(paymentScheduleService.findAll());

    }

    private void calculation() {
        paymentScheduleService.deleteAllPaymentSchedule();
        double remains = creditOffer.getAmountOfCredit();
        double percentYearRate = (creditOffer.getCredit().getPercentOfCredit() / (100 * 12));

        double paymentAmount = remains * (percentYearRate + (percentYearRate / ((Math.pow((1 + percentYearRate),
                creditOffer.getCreditPeriodInMonths())) - 1)));
        double paymentAmountRound = round(paymentAmount);

        double bodyAmount;
        double percentAmount;
        PaymentSchedule newPaymentSchedule;
        double percentFinalAmount = 0.0;

        for (int i = 1; i <= creditOffer.getCreditPeriodInMonths(); i++) {
            percentAmount = remains * percentYearRate;
            bodyAmount = paymentAmount - percentAmount;
            remains = remains - bodyAmount;

            double percentAmountRound = round(percentAmount);
            double bodyAmountRound = round(bodyAmount);
            double remainsRound = round(remains);
            percentFinalAmount = percentFinalAmount + percentAmount;

            newPaymentSchedule = new PaymentSchedule(LocalDate.now().plusMonths(i), paymentAmountRound, bodyAmountRound, percentAmountRound, remainsRound);
            paymentScheduleService.savePaymentSchedule(newPaymentSchedule);
        }
        percentFinalAmountRound = round(percentFinalAmount);
    }

    public static Double round(Double valueToFormat) {
        long rounded = Math.round(valueToFormat * 1000);
        return rounded / 1000.0;
    }

    private void setContent(Tab tab) {
        content.removeAll();
        if (tab.equals(clientTab)) {
            content.add(createClientView());
        } else if (tab.equals(creditTab)) {
            content.add(createCreditView());
        } else if (tab.equals(creditOfferTab)) {
            content.add(createCreditOfferView());
        }
    }
}


