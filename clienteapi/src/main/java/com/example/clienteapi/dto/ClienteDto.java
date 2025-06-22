package com.example.clienteapi.dto;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDto {

    private Long id;

    private String nome;

    private String email;

    private String telefone;

    private String cpf;
}
