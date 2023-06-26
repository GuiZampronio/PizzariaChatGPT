package com.webdevelopment.PizzariaChatGPT.repository;

import com.webdevelopment.PizzariaChatGPT.dao.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
        @Query(value = "SELECT * FROM public.mensagem m WHERE m.cliente_id = ?1 AND m.atendimentoconcluido = false ORDER BY m.timestamp ASC",
                        nativeQuery = true)
        List<Mensagem> findActiveOldMessagesByCustomerID(Integer customerId);

        @Modifying
        @Transactional
        @Query(value = "UPDATE public.mensagem m SET atendimentoConcluido = true WHERE m.cliente_id = ?1 AND m.atendimentoConcluido = false",
                        nativeQuery = true)
        void updateOldMessagesFromCustomerAsFinished(Integer customerId);
}
