package com.lapshovi.HaulmontTestTask.repository;

import com.lapshovi.HaulmontTestTask.model.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CreditRepository extends JpaRepository<Credit, UUID> {
    List<Credit> findByNameOfCreditStartsWithIgnoreCase(String name);
}
