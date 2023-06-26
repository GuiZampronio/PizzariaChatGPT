package com.webdevelopment.PizzariaChatGPT.service;

import com.webdevelopment.PizzariaChatGPT.dao.Cliente;
import com.webdevelopment.PizzariaChatGPT.dto.MetaPackageDTO.NotificationMetaDTO;
import com.webdevelopment.PizzariaChatGPT.repository.ClienteRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class MessageProcessorService {
        @Autowired
        private MetaRestService metaRestService;

        @Autowired
        private OpenAIRestService openAIRestService;

        @Autowired
        private ClienteRepository clienteRepository;

        public HttpStatus execute(NotificationMetaDTO notification){
                try{
                        String sendtoPhoneNumber = notification.getEntry().get(0).getChanges().get(0).getValue().getMessages().get(0).getFrom();
                        String messageToSend = notification.getEntry().get(0).getChanges().get(0).getValue().getMessages().get(0).getText().getBody();
                        String waID = notification.getEntry().get(0).getChanges().get(0).getValue().getContacts().get(0).getWa_id();;
                        log.info(notification);

                        verifyNewClient(notification);

                        String responseFromAI = openAIRestService.sendMessageChatGPT(messageToSend,  waID);
                        log.info(responseFromAI);

                        metaRestService.sendTextMessage(sendtoPhoneNumber, responseFromAI);

                        return HttpStatus.OK;
                }catch (Exception e){
                        log.info("Not a message"); // receber uma string e logar para validar o que está sendo pego no catch e arrumar essa gambiarra
                        log.info(e.getMessage());
                        return HttpStatus.OK;
                }


        }

        private void verifyNewClient(NotificationMetaDTO notification){
                String waIdFromNotification = notification.getEntry().get(0).getChanges().get(0).getValue().getContacts().get(0).getWa_id();
                Boolean validCustomer = clienteRepository.checkIfCustomerExist(waIdFromNotification);

                if (validCustomer){
                        log.info("Cliente já cadastrado!");
                }else{
                        registerNewCustomer(notification);
                }

        }

        private void registerNewCustomer(NotificationMetaDTO notificationMetaDTO){
                String waId = notificationMetaDTO.getEntry().get(0).getChanges().get(0).getValue().getContacts().get(0).getWa_id();
                String nome = notificationMetaDTO.getEntry().get(0).getChanges().get(0).getValue().getContacts().get(0).getProfile().getName();
                String client_number = notificationMetaDTO.getEntry().get(0).getChanges().get(0).getValue().getMessages().get(0).getFrom();
                Cliente newCustomer = Cliente.builder()
                          .wa_id(waId)
                          .client_number(client_number)
                          .nome(nome)
                          .build();

                Cliente savedCustomer = clienteRepository.save(newCustomer);
                log.info("Novo Cliente Cadastrado: ");
                log.info(savedCustomer.toString());
        }

}
