package com.felipe.arqsoftware.demo.controller;

import com.felipe.arqsoftware.demo.dto.ContaCorrenteDto;
import com.felipe.arqsoftware.demo.model.ContaCorrente;
import com.felipe.arqsoftware.demo.service.ContaCorrenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.security.auth.login.AccountNotFoundException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/contas")
public class ContaCorrenteController {

    @Autowired
    ContaCorrenteService service;

    @GetMapping
    public ResponseEntity<List<ContaCorrente>> findAll() {
        List<ContaCorrente> contas = service.findAll();
        return ResponseEntity.ok().body(contas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaCorrenteDto> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(service.findById(id));
        } catch (AccountNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<ContaCorrente> createAccount(@RequestBody ContaCorrente contaCorrente) {
        ContaCorrente conta = service.createAccount(contaCorrente);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(contaCorrente.getId()).toUri();
        return ResponseEntity.created(uri).body(conta);
    }

    @PutMapping ResponseEntity<ContaCorrente> updateAccount(@RequestBody ContaCorrenteDto contaCorrenteDto) {
        return ResponseEntity.accepted().body(service.updateAccount(contaCorrenteDto));
    }

    @DeleteMapping("/{id}")
    public void deleteAccountById(@PathVariable Long id) {
        service.deleteAccountById(id);
    }
}
