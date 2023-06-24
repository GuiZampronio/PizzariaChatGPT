package com.webdevelopment.PizzariaChatGPT.service;

import com.google.gson.Gson;
import com.webdevelopment.PizzariaChatGPT.dto.OpenAIPackageDTO.GPTResponse;
import com.webdevelopment.PizzariaChatGPT.dto.OpenAIPackageDTO.MessageChatDTO;
import com.webdevelopment.PizzariaChatGPT.dto.OpenAIPackageDTO.RequestBodyChatGPTDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
@Log4j2
public class OpenAIRestService {
        @Autowired
        private Gson gson;

        @Autowired
        private RestTemplate restTemplate;

        @Autowired
        private ContextService contextService;

        final private String openAIKey = "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";

        final private String chatURL = "https://api.openai.com/v1/chat/completions";

        public String sendMessageChatGPT(String newMessage, String waID){

                ArrayList<MessageChatDTO> messageToSend = contextService.buildContext(newMessage, waID);

                RequestBodyChatGPTDTO requestBody = RequestBodyChatGPTDTO.builder()
                          .model("gpt-3.5-turbo")
                          .temperature(0.4)
                          .messages(messageToSend)
                          .build();

                String requestBodyJSON = gson.toJson(requestBody);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setBearerAuth(openAIKey);

                HttpEntity<String> request = new HttpEntity<>(requestBodyJSON, headers);

                GPTResponse response = restTemplate.postForObject(chatURL, request, GPTResponse.class);
                log.info(response);

                String responseFromGPT = response.getChoices().get(0).getMessage().getContent();

                contextService.addNewMessageWithResponseAtDatabase(newMessage, responseFromGPT, waID);

                if(responseFromGPT.contains("<Finalizado>")){
                        log.info("Pedido foi considerado finalizado pelo GPT, marcando mensagens antigas como atendidas...");
                        String finalResponseFromGPT = responseFromGPT.replace("<Finalizado>", "");
                        contextService.markAllOldMessagesAsFinished(waID);
                        return finalResponseFromGPT;
                }else{
                        return responseFromGPT;
                }

        }
}
