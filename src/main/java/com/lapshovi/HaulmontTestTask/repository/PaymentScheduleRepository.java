package com.lapshovi.HaulmontTestTask.repository;

import com.lapshovi.HaulmontTestTask.model.CreditOffer;
import com.lapshovi.HaulmontTestTask.model.PaymentSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentScheduleRepository extends JpaRepository<PaymentSchedule, UUID> {
    PaymentSchedule findByCreditOffer(CreditOffer creditOffer);

}
