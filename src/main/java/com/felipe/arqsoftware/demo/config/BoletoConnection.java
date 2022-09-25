package com.felipe.arqsoftware.demo.config;


import com.felipe.arqsoftware.demo.dto.ContaCorrenteDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@FeignClient(url = "http://localhost:8081/boletos/pagar")
public interface BoletoConnection {
    @PostMapping
    public void pagarBoleto(@RequestBody ContaCorrenteDto contaCorrenteDto);
}
