package com.lapshovi.HaulmontTestTask.service;

import com.lapshovi.HaulmontTestTask.model.CreditOffer;
import com.lapshovi.HaulmontTestTask.repository.CreditOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CreditOfferService {
    private final CreditOfferRepository creditOfferRepository;

    @Autowired
    public CreditOfferService(CreditOfferRepository creditOfferRepository) {
        this.creditOfferRepository = creditOfferRepository;
    }

    public CreditOffer findById(UUID id) {
        return creditOfferRepository.getById(id);
    }

    public List<CreditOffer> findAll() {
        return creditOfferRepository.findAll();
    }

    public CreditOffer saveCreditOffer(CreditOffer creditOffer) {
        return creditOfferRepository.save(creditOffer);
    }

    public void deleteById(UUID id) {
        creditOfferRepository.deleteById(id);
    }

    public void deleteCreditoffer(CreditOffer creditOffer) {
        creditOfferRepository.delete(creditOffer);
    }
}


