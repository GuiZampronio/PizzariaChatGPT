package com.webdevelopment.PizzariaChatGPT.repository;

import com.webdevelopment.PizzariaChatGPT.dto.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
}