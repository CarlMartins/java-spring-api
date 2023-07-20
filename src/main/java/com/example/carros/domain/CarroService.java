package com.example.carros.domain;

import com.example.carros.domain.dto.CarroDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class CarroService {

    @Autowired
    private CarroRepository rep;

    public List<CarroDto> getCarros() {
        return rep.findAll().stream().map(CarroDto::new).toList();
    }

    public Optional<CarroDto> getCarroById(Long id) {
        return rep.findById(id).map(CarroDto::new);
    }

    public List<CarroDto> GetCarrosByTipo(String tipo) {
        return rep.findByTipo(tipo).stream().map(CarroDto::new).toList();
    }

    public void save(Carro carro) {
        rep.save(carro);
    }

    public void update(Carro carro, Long id) {
        Assert.notNull(id, "Não foi possível atualizar o registro");

        Optional<Carro> c = rep.findById(id);

        if (c.isEmpty()){
            throw new RuntimeException("Não foi possível atualizar o registro");
        }

        Carro db = c.get();
        db.setNome(carro.getNome());
        db.setTipo(carro.getTipo());

        rep.save(db);
    }

    public void delete(Long id) {
        Optional<CarroDto> carro = getCarroById(id);

        if (carro.isEmpty()){
            throw new RuntimeException("Não foi possível deletar o registro");
        }

        rep.deleteById(id);
    }
}
