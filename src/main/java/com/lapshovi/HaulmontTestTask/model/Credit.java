package com.lapshovi.HaulmontTestTask.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "credit")
public class Credit {
    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    private String nameOfCredit;
    @NotNull
    private Long limitOfCredit;
    @NotNull
    private Double percentOfCredit;

    @OneToMany(mappedBy = "credit")
    private List<CreditOffer> creditOffers;

    public Credit() {
    }

    public Credit(String nameOfCredit, Long limitOfCredit, Double percentOfCredit) {
        this.nameOfCredit = nameOfCredit;
        this.limitOfCredit = limitOfCredit;
        this.percentOfCredit = percentOfCredit;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNameOfCredit() {
        return nameOfCredit;
    }

    public void setNameOfCredit(String nameOfCredit) {
        this.nameOfCredit = nameOfCredit;
    }

    public Long getLimitOfCredit() {
        return limitOfCredit;
    }

    public void setLimitOfCredit(Long limitOfCredit) {
        this.limitOfCredit = limitOfCredit;
    }

    public Double getPercentOfCredit() {
        return percentOfCredit;
    }

    public void setPercentOfCredit(Double percentOfCredit) {
        this.percentOfCredit = percentOfCredit;
    }

    public List<CreditOffer> getCreditOffers() {
        return creditOffers;
    }

    public void setCreditOffers(List<CreditOffer> creditOffers) {
        this.creditOffers = creditOffers;
    }

    @Override
    public String toString() {
        return nameOfCredit;
    }
}
