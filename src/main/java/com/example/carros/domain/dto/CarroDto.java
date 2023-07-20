package com.example.carros.domain.dto;

import com.example.carros.domain.Carro;

public class CarroDto {
    public Long id;
    public String nome;
    public String tipo;

    public CarroDto(Carro c) {
        this.id = c.getId();
        this.nome = c.getNome();
        this.tipo = c.getTipo();
    }
}
