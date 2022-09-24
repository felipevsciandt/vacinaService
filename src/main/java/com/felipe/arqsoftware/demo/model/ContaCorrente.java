package com.felipe.arqsoftware.demo.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class ContaCorrente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int agencia;
    private int numeroConta;
    private String cliente;
    private Double saldo;
    @OneToMany(mappedBy = "contaCorrente")
    private List<Extrato> extratos;

    public ContaCorrente (Long id, int agencia, int numeroConta){
        this.id = id;
        this.agencia = agencia;
        this.numeroConta = numeroConta;
    }

    public ContaCorrente(Long id, int agencia, int numeroConta, String cliente, Double saldo) {
        this.id = id;
        this.agencia = agencia;
        this.numeroConta = numeroConta;
        this.cliente = cliente;
        this.saldo = saldo;
    }

    public void sacar(Double quantidade) {
        Double saldoAnterior = this.getSaldo();
        this.setSaldo(this.getSaldo() - quantidade);
        gerarExtratoSaque(this, saldoAnterior);

    }

    public void depositar(ContaCorrente contaDeposita, ContaCorrente contaRecebe, Double valor) {
        contaDeposita.setSaldo(contaDeposita.getSaldo() - valor);
        contaRecebe.setSaldo(contaRecebe.getSaldo() + valor);
        gerarExtratoSaque(contaDeposita, contaDeposita.getSaldo() + valor);
        gerarExtratoDeposito(contaRecebe, valor);
    }


    public Extrato gerarExtratoSaque(ContaCorrente conta, Double saldoAnterior) {
        return new Extrato(conta, saldoAnterior, conta.getSaldo(), conta.getSaldo() - saldoAnterior);
    }

    private Extrato gerarExtratoDeposito(ContaCorrente conta, Double valor) {
        return new Extrato(conta, conta.getSaldo() - valor, valor, conta.getSaldo());
    }

}
