package com.webdevelopment.PizzariaChatGPT.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class Usuario {
    private String id;
    private String endereco;
    private String contato;
    private String listaProduto;
}
