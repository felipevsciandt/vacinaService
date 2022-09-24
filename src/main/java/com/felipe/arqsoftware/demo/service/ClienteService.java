package com.felipe.arqsoftware.demo.service;

import com.felipe.arqsoftware.demo.model.Cliente;
import com.felipe.arqsoftware.demo.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository repository;


    @Transactional
    public List<Cliente> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Cliente findById(Long id) {
        return repository.findById(id).get();
    }

    @Transactional
    public Cliente createCliente(Cliente cliente) {
        return repository.save(cliente);
    }
}
