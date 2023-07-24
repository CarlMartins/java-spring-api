package com.example.carros.api;

import com.example.carros.domain.Carro;
import com.example.carros.domain.CarroService;
import com.example.carros.domain.dto.CarroDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

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
        CarroDto carro = service.getCarroById(id);

        return ResponseEntity.ok(carro);
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<CarroDto>> getCarrosByTipo(@PathVariable("tipo") String tipo) {
        List<CarroDto> carros = service.GetCarrosByTipo(tipo);

        return carros.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(carros);
    }

    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<CarroDto> post(@RequestBody Carro carro) {
        CarroDto c = service.insert(carro);

        URI location = getUri(c.getId());
        return ResponseEntity.created(location).build();
    }

    private URI getUri(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarroDto> put(@PathVariable("id") Long id, @RequestBody Carro carro) {
        carro.setId(id);

        CarroDto c = service.update(carro, id);

        return c != null
                ? ResponseEntity.ok(c)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        service.delete(id);

        return ResponseEntity.ok().build();
    }
}
