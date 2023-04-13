package com.webdevelopment.PizzariaChatGPT.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.webdevelopment.PizzariaChatGPT.dto.MetaPackageDTO.NotificationMetaDTO;
import com.webdevelopment.PizzariaChatGPT.dto.MetaPackageDTO.SendMessageDTO;
import com.webdevelopment.PizzariaChatGPT.dto.MetaPackageDTO.TextDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Log4j2
public class MetaRestService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Gson gson;

    final private String metaURL = "https://graph.facebook.com/v16.0/";

    public void sendTextMessage(String toPhoneNumber, String messageToSend){
        String finalUrl = metaURL.concat("/PHONE_NUMBER_ID/messages");
        SendMessageDTO sendMessage = SendMessageDTO.builder()
                .messaging_product("whatsapp")
                .recipient_type("individual")
                .to(toPhoneNumber)
                .type("text")
                .text(new TextDTO(messageToSend, false))
                .build();

        String sendMessageJson = gson.toJson(sendMessage);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth("XXXXXXXXXXX");

        HttpEntity<String> request = new HttpEntity<>(sendMessageJson, headers);

        String response = restTemplate.postForObject(finalUrl, request, String.class);
        log.info(response);

    }
}
