package com.webdevelopment.PizzariaChatGPT.dao;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
@Table(name = "cliente")
public class Cliente {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        private String wa_id;
        private String client_number;
        private String nome;
}
