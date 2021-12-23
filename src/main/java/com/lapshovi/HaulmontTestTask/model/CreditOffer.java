package com.lapshovi.HaulmontTestTask.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "credit_offer")
public class CreditOffer {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "credit_id")
    private Credit credit;

    private Long amountOfCredit;
    private Integer creditPeriodInMonths;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "paymentschedule_id")
    private PaymentSchedule paymentSchedule;


    public CreditOffer() {
    }

    public CreditOffer(Client client, Credit credit, Long amountOfCredit, Integer creditPeriod) {
        this.client = client;
        this.credit = credit;
        this.amountOfCredit = amountOfCredit;
        this.creditPeriodInMonths = creditPeriod;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }

    public Long getAmountOfCredit() {
        return amountOfCredit;
    }

    public void setAmountOfCredit(Long amountCredit) {
        this.amountOfCredit = amountCredit;
    }

    public Integer getCreditPeriodInMonths() {
        return creditPeriodInMonths;
    }

    public void setCreditPeriodInMonths(Integer creditPeriodInMonths) {
        this.creditPeriodInMonths = creditPeriodInMonths;
    }

    public PaymentSchedule getPaymentSchedule() {
        return paymentSchedule;
    }

    public void setPaymentSchedule(PaymentSchedule paymentSchedule) {
        this.paymentSchedule = paymentSchedule;
    }

}

