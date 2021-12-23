package com.lapshovi.HaulmontTestTask.service;

import com.lapshovi.HaulmontTestTask.model.CreditOffer;
import com.lapshovi.HaulmontTestTask.model.PaymentSchedule;
import com.lapshovi.HaulmontTestTask.repository.PaymentScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentScheduleService {
    private final PaymentScheduleRepository paymentScheduleRepository;

    @Autowired
    public PaymentScheduleService(PaymentScheduleRepository paymentScheduleRepository) {
        this.paymentScheduleRepository = paymentScheduleRepository;
    }

    public PaymentSchedule findById(UUID id) {
        return paymentScheduleRepository.getById(id);
    }

    public List<PaymentSchedule> findAll() {
        return paymentScheduleRepository.findAll();
    }

    public PaymentSchedule findByCreditOffer(CreditOffer creditOffer) {
        return paymentScheduleRepository.findByCreditOffer(creditOffer);
    }


    public PaymentSchedule savePaymentSchedule(PaymentSchedule paymentSchedule) {
        return paymentScheduleRepository.save(paymentSchedule);
    }

    public void deleteById(UUID id) {
        paymentScheduleRepository.deleteById(id);
    }

    public void deletePaymentSchedule(PaymentSchedule paymentSchedule) {
        paymentScheduleRepository.delete(paymentSchedule);
    }

    public void deleteAllPaymentSchedule() {
        paymentScheduleRepository.deleteAll();
    }

}

