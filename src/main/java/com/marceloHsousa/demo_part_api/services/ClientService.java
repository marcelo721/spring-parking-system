package com.marceloHsousa.demo_part_api.services;

import com.marceloHsousa.demo_part_api.entities.Client;
import com.marceloHsousa.demo_part_api.repositories.ClientRepository;
import com.marceloHsousa.demo_part_api.services.exceptions.CpfUniqueViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    @Transactional
    public Client insert(Client client){

        try {
            return clientRepository.save(client);

        }catch (DataIntegrityViolationException e){

            throw  new CpfUniqueViolationException(String.format("The CPF '%s' is already registered", client.getCpf()));
        }
    }
}
