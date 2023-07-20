package com.example.carros.api;

import com.example.carros.domain.Carro;
import com.example.carros.domain.CarroService;
import com.example.carros.domain.dto.CarroDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/carros")
public class CarrosController {
    @Autowired
    private CarroService service;

    @GetMapping
    public ResponseEntity<Iterable<CarroDto>> get() {
        return ResponseEntity.ok(service.getCarros());
    }

    @GetMapping("{id}")
    public ResponseEntity<CarroDto> get(@PathVariable("id") Long id) {
        Optional<CarroDto> carro = service.getCarroById(id);

        return carro
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<CarroDto>> getCarrosByTipo(@PathVariable("tipo") String tipo) {
        List<CarroDto> carros = service.GetCarrosByTipo(tipo);

        return carros.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(carros);
    }

    @PostMapping
    public String post(@RequestBody Carro carro) {
        service.save(carro);

        return "Carro salvo com sucesso! " + carro.getNome();
    }

    @PutMapping("/{id}")
    public String put(@PathVariable("id") Long id, @RequestBody Carro carro) {
        service.update(carro, id);

        return "Carro atualizado com sucesso! " + carro.getNome();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        service.delete(id);

        return "Carro deletado com sucesso!";
    }
}
