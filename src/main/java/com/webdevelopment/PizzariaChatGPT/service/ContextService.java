package com.webdevelopment.PizzariaChatGPT.service;

import com.webdevelopment.PizzariaChatGPT.dao.Mensagem;
import com.webdevelopment.PizzariaChatGPT.dto.OpenAIPackageDTO.MessageChatDTO;
import com.webdevelopment.PizzariaChatGPT.repository.ClienteRepository;
import com.webdevelopment.PizzariaChatGPT.repository.MensagemRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class ContextService {

        @Autowired
        private ClienteRepository clienteRepository;

        @Autowired
        private MensagemRepository mensagemRepository;

        public ArrayList<MessageChatDTO> buildContext(String newMessage, String waID){

                ArrayList<MessageChatDTO> messagesToSend = new ArrayList<>();

                MessageChatDTO InitialContext =generateInitialContext();
                messagesToSend.add(InitialContext);

                ArrayList<MessageChatDTO> oldMessages = getOldMessagesFromCustomer(waID);

                messagesToSend.addAll(oldMessages);

                messagesToSend.add(MessageChatDTO.builder().role("user").content(newMessage).build());

                return messagesToSend;
        }

        public void addNewMessageWithResponseAtDatabase(String messageReceived, String chatGPTResponse, String waID){
                Integer customerID = clienteRepository.findCustomerIDByWaID(waID);

                Mensagem mensagem = Mensagem.builder()
                          .respostaMensagem(chatGPTResponse)
                          .mensagemRecebida(messageReceived)
                          .atendimentoConcluido(false)
                          .clientId(customerID)
                          .timestamp(new Timestamp(System.currentTimeMillis()))
                          .build();

                mensagemRepository.save(mensagem);

        }

        public void markAllOldMessagesAsFinished(String waID){
                Integer customerID = clienteRepository.findCustomerIDByWaID(waID);
                mensagemRepository.updateOldMessagesFromCustomerAsFinished(customerID);
        }


        private ArrayList<MessageChatDTO> getOldMessagesFromCustomer(String waID){
                Integer customerID = clienteRepository.findCustomerIDByWaID(waID);
                log.info("Fetch old messages from customer {}", customerID);
                List<Mensagem> oldMessagesList = mensagemRepository.findActiveOldMessagesByCustomerID(customerID);

                ArrayList<MessageChatDTO> oldMessagesChatDTO = new ArrayList<>();

                if(oldMessagesList.isEmpty()){
                        log.info("Old Active messages not found!");
                }else{
                        for (Mensagem message : oldMessagesList){
                                oldMessagesChatDTO.add(MessageChatDTO.builder().role("user").content(message.getMensagemRecebida()).build());
                                oldMessagesChatDTO.add(MessageChatDTO.builder().role("system").content(message.getRespostaMensagem()).build());
                        }
                }

                return oldMessagesChatDTO;

        }

        private MessageChatDTO generateInitialContext(){
                MessageChatDTO initialContext = new MessageChatDTO("system",  "ChatGPT você atuará como um atendente de uma pizzaria chamada Os Discipulos de Davi, responda as mensagens se passando o máximo possível como um humano. Segue informações da pizzaria/contexto:" +
                         "\\nAtua com delivery ou retirada no local, para concluir o pedido é estritamente necessário confirmar com o cliente a maneira que ele gostaria de receber a pizza" +
                          "\\nEndereço da pizzaria : Av 24 A, 1515" +
                          "\\nTempo de entrega estimado: 45 a 60 min" +
                          "\\nPizzas artesanais com ingredientes de excelente qualidade" +
                          "\\nSimule um cardápio de uma pizzaria brasileira comum sem ser necessário muitas opções, contendo também um cardápio de bebidas, ambos com os respectivos valores" +
                          "\\nPara o funcionamento de nossa API, É EXTREMANTE IMPORTANTE quando você entender que o atendimento foi concluído e o pedido registrado, responda a mensagem para o cliente confirmando o pedido, porém iniciando a mensagem exatamente com a seguinte string:<Finalizado>");
                return initialContext;
        }




}
