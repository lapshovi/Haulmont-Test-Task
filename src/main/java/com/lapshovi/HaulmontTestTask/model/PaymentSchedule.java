package com.lapshovi.HaulmontTestTask.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "payment_shedule")
public class PaymentSchedule {
    @Id
    @GeneratedValue
    private UUID id;

    private LocalDate paymentDate;
    private Double paymentAmount;
    private Double bodyAmount;
    private Double percentAmount;
    private Double remains;

    @OneToOne(mappedBy = "paymentSchedule",fetch = FetchType.EAGER)
    private CreditOffer creditOffer;

    public PaymentSchedule() {
    }

    public PaymentSchedule(LocalDate paymentDate, Double paymentAmount, Double bodyAmount, Double percentAmount, Double remains) {
        this.paymentDate = paymentDate;
        this.paymentAmount = paymentAmount;
        this.bodyAmount = bodyAmount;
        this.percentAmount = percentAmount;
        this.remains = remains;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Double getBodyAmount() {
        return bodyAmount;
    }

    public void setBodyAmount(Double bodyAmount) {
        this.bodyAmount = bodyAmount;
    }

    public Double getPercentAmount() {
        return percentAmount;
    }

    public void setPercentAmount(Double percentAmount) {
        this.percentAmount = percentAmount;
    }

    public CreditOffer getCreditOffer() {
        return creditOffer;
    }

    public void setCreditOffer(CreditOffer creditOffer) {
        this.creditOffer = creditOffer;
    }

    public Double getRemains() {
        return remains;
    }

    public void setRemains(Double remains) {
        this.remains = remains;
    }
}

