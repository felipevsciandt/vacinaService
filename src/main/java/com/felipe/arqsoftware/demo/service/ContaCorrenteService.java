package com.felipe.arqsoftware.demo.service;

import com.felipe.arqsoftware.demo.dto.ContaCorrenteDto;
import com.felipe.arqsoftware.demo.model.ContaCorrente;
import com.felipe.arqsoftware.demo.repository.ContaCorrenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class ContaCorrenteService {

    @Autowired
    ContaCorrenteRepository repository;

    @Autowired
    ExtratoService extratoService;

    @Transactional
    public List<ContaCorrente> findAll() {
        return repository.findAll();
    }
    @Transactional
    public ContaCorrenteDto findById(Long id) throws AccountNotFoundException {
        var conta = repository.findById(id);
        ContaCorrente account = conta.orElseThrow(() -> new AccountNotFoundException("Operation failed. No account valid for the id on the output."));
        return new ContaCorrenteDto(account);
    }

    @Transactional
    public ContaCorrente createAccount(ContaCorrente contaCorrente) {
        return repository.save(contaCorrente);
    }

    @Transactional
    public void deleteAccountById(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public ContaCorrente updateAccount(ContaCorrenteDto contaCorrenteDto) {
        var conta = contaCorrenteDto.convertToContaCorrente();
        return repository.save(conta);
    }

    public ContaCorrente sacar(Long id ,Double quantidade) {
        ContaCorrente conta = repository.findById(id).get();
        Double saldoAnterior = conta.getSaldo();
        conta.setSaldo(conta.getSaldo() - quantidade);
        extratoService.gerarExtratoSaque(conta, saldoAnterior);

        return repository.save(conta);
    }

    @Transactional
    public ContaCorrente depositar(Long id, Double valor, Long id2) {
        ContaCorrente contaDeposita = repository.findById(id).get();
        ContaCorrente contaRecebe = repository.findById(id2).get();
        contaDeposita.setSaldo(contaDeposita.getSaldo() - valor);
        contaRecebe.setSaldo(contaRecebe.getSaldo() + valor);
        extratoService.gerarExtratoSaque(contaDeposita, contaDeposita.getSaldo() + valor);
        extratoService.gerarExtratoDeposito(contaRecebe, valor);

        return repository.save(contaDeposita);
    }


}
