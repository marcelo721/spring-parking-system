package com.marceloHsousa.demo_part_api.web.dto.clientDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.StandardException;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientResponseDto {

    private Long id;

    private String name;

    private String cpf;

}
