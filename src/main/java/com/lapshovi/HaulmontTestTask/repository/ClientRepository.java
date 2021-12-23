package com.lapshovi.HaulmontTestTask.repository;


import com.lapshovi.HaulmontTestTask.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
    List<Client> findByNameStartsWithIgnoreCase(String name);
}
