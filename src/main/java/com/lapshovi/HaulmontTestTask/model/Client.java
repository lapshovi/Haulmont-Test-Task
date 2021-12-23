package com.lapshovi.HaulmontTestTask.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    private String name;


    private Long phoneNumber;
    private String email;

    @NotNull
    private Long passportNumber;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<CreditOffer> creditOffers;

    public Client() {
    }

    public Client(String name, Long phoneNumber, String email, Long passportNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.passportNumber = passportNumber;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(Long passportNumber) {
        this.passportNumber = passportNumber;
    }

    public List<CreditOffer> getCreditOffers() {
        return creditOffers;
    }

    public void setCreditOffers(List<CreditOffer> creditOffers) {
        this.creditOffers = creditOffers;
    }

    @Override
    public String toString() {
        return name;
    }
}
