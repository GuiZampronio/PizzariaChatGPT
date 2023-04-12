package com.webdevelopment.PizzariaChatGPT.dto;

import lombok.Data;

@Data
public class Mensagem {
    private String id;
    private String lado;
    private String mensagem;
    private String correcao;
    private String timestamp;
}
