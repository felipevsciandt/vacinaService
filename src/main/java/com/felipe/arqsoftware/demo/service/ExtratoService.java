package com.felipe.arqsoftware.demo.service;

import com.felipe.arqsoftware.demo.model.Extrato;
import com.felipe.arqsoftware.demo.repository.ExtratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExtratoService {

    @Autowired
    private ExtratoRepository repository;

    public List<Extrato> findAll() {
        return repository.findAll();
    }

    public Extrato findById(Long id) {
        return repository.findById(id).get();
    }
}
