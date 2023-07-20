package com.example.carros.api;

import com.example.carros.domain.Carro;
import com.example.carros.domain.CarroService;
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
    public ResponseEntity<Iterable<Carro>> get() {
        return ResponseEntity.ok(service.getCarros());
    }

    @GetMapping("{id}")
    public ResponseEntity<Carro> get(@PathVariable("id") Long id) {
        Optional<Carro> carro = service.getCarroById(id);

        return carro
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<Iterable<Carro>> getCarrosByTipo(@PathVariable("tipo") String tipo) {
        List<Carro> carros = service.GetCarrosByTipo(tipo);

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
