package com.example.clienteapi.controller;

import com.example.clienteapi.dto.ClienteDto;
import com.example.clienteapi.service.ClienteService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private ClienteService service;

    @Autowired
    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ClienteDto> salvarCliente(@Valid @RequestBody ClienteDto clienteDto) {
        return new ResponseEntity<>(service.salvar(clienteDto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<ClienteDto> listarTodosClientes() {
        return service.listarTodosClientes();
    }

    @GetMapping("/nomes")
    public List<ClienteDto> buscarClientePorNome(@RequestParam String nome) {
        return service.buscarClientePorNome(nome);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> buscarClientePorId(@PathVariable Long id) {
        return service.buscarClientePorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDto> atualizarCliente(@PathVariable Long id, @RequestBody ClienteDto clienteDto) {
        try {
            clienteDto.setId(id);
            ClienteDto atualizado = service.salvar(clienteDto);
            return ResponseEntity.ok(atualizado);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
        try {
            service.deletarClientePorId(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/quantidade")
    public ResponseEntity<Long> contarClientes() {
        Long quantidade = service.contarClientes();
        return ResponseEntity.ok(quantidade);
    }
}
