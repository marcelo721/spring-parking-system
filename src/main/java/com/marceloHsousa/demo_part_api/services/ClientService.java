package com.marceloHsousa.demo_part_api.services;

import com.marceloHsousa.demo_part_api.entities.Client;
import com.marceloHsousa.demo_part_api.entities.User;
import com.marceloHsousa.demo_part_api.repositories.ClientRepository;
import com.marceloHsousa.demo_part_api.repositories.projection.ClientProjection;
import com.marceloHsousa.demo_part_api.services.exceptions.CpfUniqueViolationException;
import com.marceloHsousa.demo_part_api.services.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    @Transactional(readOnly = true)
    public Client findClientById(Long id){
        Optional<Client> obj = clientRepository.findById(id);
        return obj.orElseThrow((()-> new EntityNotFoundException("Client not Found")));
    }

    @Transactional(readOnly = true)
    public Page<ClientProjection> findAllClients(Pageable pageable){
        return clientRepository.findAllClients(pageable);
    }

    @Transactional(readOnly = true)
    public Client findUserByid(Long id){
        return clientRepository.findByUserId(id);
    }
}
