package com.webdevelopment.PizzariaChatGPT.service;

import com.webdevelopment.PizzariaChatGPT.dto.MetaPackageDTO.NotificationMetaDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class MessageProcessorService {
        @Autowired
        private MetaRestService metaRestService;

        @Autowired
        private OpenAIRestService openAIRestService;

        public HttpStatus execute(NotificationMetaDTO notification){
                try{
                        String sendtoPhoneNumber = notification.getEntry().get(0).getChanges().get(0).getValue().getMessages().get(0).getFrom();
                        String messageToSend = notification.getEntry().get(0).getChanges().get(0).getValue().getMessages().get(0).getText().getBody();
                        log.info(notification);
                        String responseFromAI = openAIRestService.sendMessageChatGPT(messageToSend);
                        log.info(responseFromAI);
                        metaRestService.sendTextMessage(sendtoPhoneNumber, responseFromAI);
                        return HttpStatus.OK;
                }catch (Exception e){
                        log.info("Not a message");
                        return HttpStatus.OK;
                }







        }
}
