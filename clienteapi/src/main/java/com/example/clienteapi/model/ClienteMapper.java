package com.example.clienteapi.model;

import com.example.clienteapi.dto.ClienteDto;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class ClienteMapper {

    public Cliente toEntity(ClienteDto clienteDto) {
        if (isNull(clienteDto)) {
            return null;
        }

        return Cliente.builder()
                .nome(clienteDto.getNome())
                .email(clienteDto.getEmail())
                .telefone(clienteDto.getTelefone())
                .cpf(clienteDto.getCpf())
                .build();
    }

    public ClienteDto toClienteDto(Cliente cliente) {
        if (isNull(cliente)) {
            return null;
        }

        return ClienteDto.builder()
                .id(cliente.getId())
                .nome(cliente.getNome())
                .email(cliente.getEmail())
                .telefone(cliente.getTelefone())
                .cpf(cliente.getCpf())
                .build();
    }
}
