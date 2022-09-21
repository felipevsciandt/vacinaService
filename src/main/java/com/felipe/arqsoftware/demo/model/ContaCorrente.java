package com.felipe.arqsoftware.demo.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContaCorrente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int agencia;
    private int numeroConta;
    private String cliente;
    private Double saldo;

    public ContaCorrente (Long id, int agencia, int numeroConta){
        this.id = id;
        this.agencia = agencia;
        this.numeroConta = numeroConta;
    }

    /*
    @OneToMany
    private ArrayList<Extrato> extratos;

     */

}
