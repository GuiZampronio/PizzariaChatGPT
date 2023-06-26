package com.webdevelopment.PizzariaChatGPT.controller;

import com.webdevelopment.PizzariaChatGPT.dto.MetaPackageDTO.NotificationMetaDTO;
import com.webdevelopment.PizzariaChatGPT.service.MessageProcessorService;
import com.webdevelopment.PizzariaChatGPT.service.MetaRestService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/webhook")
@Log4j2
public class MetaWebHookController {

      @Autowired
      private MetaRestService metaRestService;

      @Autowired
      private MessageProcessorService messageProcessorService;

      @Value("${chaveWebHook}")
      private String chaveWebHook;

      @GetMapping
      public Integer authenticateChallenge(@RequestParam(name = "hub.mode") String mode, @RequestParam(name = "hub.challenge") Integer challenge,  @RequestParam(name = "hub.verify_token") String verify_token){
            if(verify_token.equals(chaveWebHook)){
                  return challenge;
            }else{
                  return null;
            }
      }

      @PostMapping(consumes = "application/json")
      public HttpStatus receiveMessage(@RequestBody NotificationMetaDTO notification){
            HttpStatus response = messageProcessorService.execute(notification);
            return response;
      }

}

