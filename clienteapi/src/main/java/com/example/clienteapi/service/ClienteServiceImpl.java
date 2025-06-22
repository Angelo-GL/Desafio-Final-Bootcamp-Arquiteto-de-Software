package com.example.clienteapi.service;


import com.example.clienteapi.dto.ClienteDto;
import com.example.clienteapi.model.Cliente;
import com.example.clienteapi.model.ClienteMapper;
import com.example.clienteapi.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public ClienteServiceImpl(ClienteRepository clienteRepository,
                              ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    @Override
    public ClienteDto salvar(ClienteDto clienteDto) {
        Cliente cliente;

        if (clienteDto.getId() != null) {
            cliente = clienteRepository.findById(clienteDto.getId())
                    .map(clienteAtualizado -> {
                        clienteAtualizado.setNome(clienteDto.getNome());
                        clienteAtualizado.setEmail(clienteDto.getEmail());
                        clienteAtualizado.setTelefone(clienteDto.getTelefone());
                        clienteAtualizado.setCpf(clienteDto.getCpf());
                        return clienteAtualizado;
                    })
                    .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com id " + clienteDto.getId()));
        } else {
            cliente = clienteMapper.toEntity(clienteDto);
        }

        if (!cliente.isCpfValido()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF inválido");
        }

        Optional<Cliente> clienteExistente = clienteRepository.findByCpf(cliente.getCpf());
        if (clienteExistente.isPresent() &&
                (cliente.getId() == null || !clienteExistente.get().getId().equals(cliente.getId()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF já cadastrado");
        }

        Cliente clienteSalvo = clienteRepository.save(cliente);
        return clienteMapper.toClienteDto(clienteSalvo);
    }

    @Override
    public List<ClienteDto> listarTodosClientes() {
        return clienteRepository.findAll()
                .stream()
                .map(clienteMapper::toClienteDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ClienteDto> buscarClientePorId(Long id) {
        return clienteRepository.findById(id)
                .map(clienteMapper::toClienteDto);
    }

    @Override
    public List<ClienteDto> buscarClientePorNome(String nome) {
        return clienteRepository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(clienteMapper::toClienteDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deletarClientePorId(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new EntityNotFoundException("Cliente não encontrado com id: " + id);
        }
        clienteRepository.deleteById(id);
    }

    @Override
    public Long contarClientes() {
        return clienteRepository.count();
    }
}
