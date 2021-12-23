package com.lapshovi.HaulmontTestTask.repository;


import com.lapshovi.HaulmontTestTask.model.CreditOffer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CreditOfferRepository extends JpaRepository<CreditOffer, UUID> {

}
