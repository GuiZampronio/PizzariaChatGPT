package com.webdevelopment.PizzariaChatGPT.repository;

import com.webdevelopment.PizzariaChatGPT.dao.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
        @Query(value = "SELECT EXISTS(SELECT * FROM public.cliente c WHERE c.wa_id = ?1)",
                        nativeQuery = true)
        Boolean checkIfCustomerExist(String waId);

        @Query(value = "SELECT c.id FROM public.cliente c WHERE c.wa_id = ?1",
                        nativeQuery = true)
        Integer findCustomerIDByWaID(String waId);


}
