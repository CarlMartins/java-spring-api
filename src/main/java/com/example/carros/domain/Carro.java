package com.example.carros.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String tipo;
    private String descricao;

    @Column(name = "url_foto")
    private String urlFoto;

    @Column(name = "url_video")
    private String urlVideo;
    private String latitude;
    private String longitude;
}
