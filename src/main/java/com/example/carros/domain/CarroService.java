package com.example.carros.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarroService {

    @Autowired
    private CarroRepository rep;

    public Iterable<Carro> getCarros() {
        return rep.findAll();
    }

    public Optional<Carro> getCarroById(Long id) {
        return rep.findById(id);
    }

    public Iterable<Carro> GetCarrosByTipo(String tipo) {
        return rep.findByTipo(tipo);
    }

    public void save(Carro carro) {
        rep.save(carro);
    }

    public void update(Carro carro, Long id) {
        Assert.notNull(id, "Não foi possível atualizar o registro");

        Optional<Carro> c = getCarroById(id);

        if (c.isEmpty()){
            throw new RuntimeException("Não foi possível atualizar o registro");
        }

        Carro db = c.get();
        db.setNome(carro.getNome());
        db.setTipo(carro.getTipo());

        rep.save(db);
    }

    public void delete(Long id) {
        Optional<Carro> carro = getCarroById(id);

        if (carro.isEmpty()){
            throw new RuntimeException("Não foi possível deletar o registro");
        }

        rep.deleteById(id);
    }
}
