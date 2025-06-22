package com.example.clienteapi.service;

import com.example.clienteapi.dto.ClienteDto;
import com.example.clienteapi.model.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteService {

    ClienteDto salvar(ClienteDto clienteDto);

    List<ClienteDto> listarTodosClientes();

    Optional<ClienteDto> buscarClientePorId(Long id);

    List<ClienteDto> buscarClientePorNome(String nome);

    void deletarClientePorId(Long id);

    Long contarClientes();

}
