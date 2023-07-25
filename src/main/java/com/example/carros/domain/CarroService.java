package com.example.carros.domain;

import com.example.carros.domain.dto.CarroDto;
import com.example.carros.domain.exception.ObjectNotFoundException;
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
        return rep.findAll().stream().map(CarroDto::create).toList();
    }

    public Optional<CarroDto> getCarroById(Long id) {
        return Optional.ofNullable(rep.findById(id)
                .map(CarroDto::create)
                .orElseThrow(() -> new ObjectNotFoundException("Carro não encontrado")));
    }

    public List<CarroDto> GetCarrosByTipo(String tipo) {
        return rep.findByTipo(tipo).stream().map(CarroDto::create).toList();
    }

    public CarroDto insert(Carro carro) {
        Assert.isNull(carro.getId(), "Não foi possível inserir o registro");

        return CarroDto.create(rep.save(carro));
    }

    public CarroDto update(Carro carro, Long id) {
        Assert.notNull(id, "Não foi possível atualizar o registro");

        Optional<Carro> c = rep.findById(id);

        if (c.isEmpty()) {
            throw new RuntimeException("Não foi possível atualizar o registro");
        }

        Carro db = c.get();
        db.setNome(carro.getNome());
        db.setTipo(carro.getTipo());

        rep.save(db);

        return CarroDto.create(db);
    }

    public void delete(Long id) {
        rep.deleteById(id);
    }
}
