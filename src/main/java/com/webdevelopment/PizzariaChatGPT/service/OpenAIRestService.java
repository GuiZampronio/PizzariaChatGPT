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

        final private String openAIKey = "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";

        final private String chatURL = "https://api.openai.com/v1/chat/completions";

        public String sendMessageChatGPT(String messageToAsk){

                ArrayList<MessageChatDTO> messageToSend = new ArrayList<>();

                MessageChatDTO context = new MessageChatDTO("system",  "ChatGPT você será um atendente de uma pizzaria, responde as mensagens de acordo. Algumas informações sobre a pizzaria: Endereço da pizzaria: Av 22 A, 1248; Cardápio: 1 - Pizza de 3 queijos - 10 reais; 2 - Pizza de 4 queijos - 20 reais; 3 - Calabresa - 30 reais; 4- Portuguesa - 40 reais; Fim do cardápio; Tempo de entrega - 45 a 60 min; Ingredientes caseiros;");
                messageToSend.add(context);

                MessageChatDTO messageChatDTO = new MessageChatDTO("user",  messageToAsk);
                messageToSend.add(messageChatDTO);


                RequestBodyChatGPTDTO requestBody = RequestBodyChatGPTDTO.builder()
                          .model("gpt-3.5-turbo")
                          .temperature(0.5)
                          .max_tokens(2048)
                          .messages(messageToSend)
                          .build();

                String requestBodyJSON = gson.toJson(requestBody);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setBearerAuth(openAIKey);

                HttpEntity<String> request = new HttpEntity<>(requestBodyJSON, headers);

                GPTResponse response = restTemplate.postForObject(chatURL, request, GPTResponse.class);
                log.info(response);
                return response.getChoices().get(0).getMessage().getContent();
        }
}
