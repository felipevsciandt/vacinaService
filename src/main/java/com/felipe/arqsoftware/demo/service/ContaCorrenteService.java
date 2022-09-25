package com.felipe.arqsoftware.demo.service;

import com.felipe.arqsoftware.demo.dto.ContaCorrenteDto;
import com.felipe.arqsoftware.demo.model.ContaCorrente;
import com.felipe.arqsoftware.demo.repository.ContaCorrenteRepository;
import com.felipe.arqsoftware.demo.service.exceptions.SaldoInsuficienteException;
import com.felipe.arqsoftware.demo.service.exceptions.AccountNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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
        validarSaldo(conta, quantidade);
        Double saldoAnterior = conta.getSaldo();
        conta.setSaldo(conta.getSaldo() - quantidade);
        extratoService.gerarExtratoSaque(conta, saldoAnterior);

        return repository.save(conta);
    }

    private void validarSaldo(ContaCorrente conta, Double quantidade) {
        if (quantidade > conta.getSaldo()) {
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar a operacao");
        }
    }

    @Transactional
    public ContaCorrente depositar(Long id, Double valor, Long id2) {
        ContaCorrente contaDeposita = repository.findById(id).get();
        validarSaldo(contaDeposita, valor);
        ContaCorrente contaRecebe = repository.findById(id2).get();
        contaDeposita.setSaldo(contaDeposita.getSaldo() - valor);
        contaRecebe.setSaldo(contaRecebe.getSaldo() + valor);
        extratoService.gerarExtratoSaque(contaDeposita, contaDeposita.getSaldo() + valor);
        extratoService.gerarExtratoDeposito(contaRecebe, valor);

        return repository.save(contaDeposita);
    }


    public void pagarBoleto(Long idConta, Double valorBoleto) {
        Optional<ContaCorrente> optionalConta = repository.findById(idConta);
        if (!optionalConta.isPresent()) {
            //Lancar excessao
        }
        ContaCorrente conta = optionalConta.get();
        if (conta.getSaldo() < valorBoleto) {
            throw new SaldoInsuficienteException("Saldo insuficiente");
        }
        conta.setSaldo(conta.getSaldo() - valorBoleto);
        repository.save(conta);
    }
}
