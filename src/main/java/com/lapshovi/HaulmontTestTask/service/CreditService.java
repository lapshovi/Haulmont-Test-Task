package com.lapshovi.HaulmontTestTask.service;

import com.lapshovi.HaulmontTestTask.model.Credit;
import com.lapshovi.HaulmontTestTask.repository.CreditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CreditService {
    private final CreditRepository creditRepository;

    @Autowired
    public CreditService(CreditRepository creditRepository) {
        this.creditRepository = creditRepository;
    }

    public Credit findById(UUID id) {
        return creditRepository.getById(id);
    }

    public List<Credit> findByName(String nameOfCredit) {
        return creditRepository.findByNameOfCreditStartsWithIgnoreCase(nameOfCredit);
    }

    public List<Credit> findAll() {
        return creditRepository.findAll();
    }

    public Credit saveCredit(Credit credit) {
        return creditRepository.save(credit);
    }

    public void deleteCredit(Credit credit) {
        creditRepository.delete(credit);
    }

    public void deleteById(UUID id) {
        creditRepository.deleteById(id);
    }
}
