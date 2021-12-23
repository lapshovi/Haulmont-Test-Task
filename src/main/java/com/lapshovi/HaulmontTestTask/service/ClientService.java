package com.lapshovi.HaulmontTestTask.service;


import com.lapshovi.HaulmontTestTask.model.Client;
import com.lapshovi.HaulmontTestTask.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client findById(UUID id) {
        return clientRepository.getById(id);
    }

    public List<Client> findByName(String name) {
        return clientRepository.findByNameStartsWithIgnoreCase(name);
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    public void deleteClient(Client client) {
        clientRepository.delete(client);
    }

    public void deleteById(UUID id) {
        clientRepository.deleteById(id);
    }

}


