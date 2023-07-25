package com.example.carros;

import com.example.carros.domain.Carro;
import com.example.carros.domain.CarroService;
import com.example.carros.domain.dto.CarroDto;
import com.example.carros.domain.exception.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CarrosApplicationTests {

    @Autowired
    private CarroService service;

    @Test
    void contextLoads() {
    }

    @Test
    void CadastrarEExcluirCarroCarro_Deve_RetornarSucessoEExcecao() {
        Carro carro = new Carro();
        carro.setNome("Ferrari");
        carro.setTipo("esportivos");

        CarroDto carroDto = service.insert(carro);

        Assert.notNull(carroDto, "O carro não pode ser nulo");

        Long id = carroDto.getId();
        Assert.notNull(id, "O id não pode ser nulo");

        Optional<CarroDto> carroDtoOptional = service.getCarroById(id);
        Assert.isTrue(carroDtoOptional.isPresent(), "O carro não foi encontrado");

        carroDto = carroDtoOptional.get();
        Assert.isTrue(carroDto.getNome().equals("Ferrari"), "O nome do carro não é igual ao esperado");
        Assert.isTrue(carroDto.getTipo().equals("esportivos"), "O tipo do carro não é igual ao esperado");

        service.delete(id);

        assertThrows(ObjectNotFoundException.class, () -> service.getCarroById(id));
    }

    @Test
    void RecuperaListaDeCarros_Deve_RetornarSucesso() {
        List<CarroDto> carros = service.getCarros();

        Assert.notEmpty(carros, "A lista não pode ser vazia");
        Assert.isTrue(carros.size() == 30, "A lista deve conter 30 carros");
    }

    @Test
    void RecuperaCarroId11_Deve_RetornarSucesso() {
        Optional<CarroDto> op = service.getCarroById(11L);

        Assert.isTrue(op.isPresent(), "O carro não foi encontrado");

        CarroDto c = op.get();

        Assert.isTrue(c.getNome().equals("Ferrari FF"), "O nome do carro não é igual ao esperado");
    }

    @Test
    void RecuperarCarrosPorTipo_Deve_RetornarSucesso() {
        Assert.isTrue(service.GetCarrosByTipo("classicos").size() == 10, "A lista deve conter 10 carros do tipo clássicos");
        Assert.isTrue(service.GetCarrosByTipo("esportivos").size() == 10, "A lista deve conter 10 carros do tipo esportivos");
        Assert.isTrue(service.GetCarrosByTipo("luxo").size() == 10, "A lista deve conter 10 carros do tipo luxo");
    }
}
