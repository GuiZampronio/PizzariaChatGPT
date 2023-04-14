package com.webdevelopment.PizzariaChatGPT.controller;

import com.webdevelopment.PizzariaChatGPT.dto.MetaPackageDTO.NotificationMetaDTO;
import com.webdevelopment.PizzariaChatGPT.service.MetaRestService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook")
@Log4j2
public class MetaWebHookController {

      @Autowired
      private MetaRestService metaRestService;

      @GetMapping
      public Integer authenticateChallenge(@RequestParam(name = "hub.mode") String mode, @RequestParam(name = "hub.challenge") Integer challenge,  @RequestParam(name = "hub.verify_token") String verify_token){
            if(verify_token.equals("chaveSuperSecreta")){
                  return challenge;
            }else{
                  return null;
            }
      }

      @PostMapping(consumes = "application/json")
      public HttpStatus receiveMessage(@RequestBody NotificationMetaDTO notification){
            log.info(notification);
            String sendtoPhoneNumber = notification.getEntry().get(0).getChanges().get(0).getValue().getMessages().get(0).getFrom();
            String messageToSend = notification.getEntry().get(0).getChanges().get(0).getValue().getMessages().get(0).getText().getBody();
            metaRestService.sendTextMessage(sendtoPhoneNumber, messageToSend);
            return HttpStatus.OK;
      }

}

