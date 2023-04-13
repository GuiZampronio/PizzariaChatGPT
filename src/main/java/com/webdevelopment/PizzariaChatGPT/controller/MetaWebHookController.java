package com.webdevelopment.PizzariaChatGPT.controller;

import com.webdevelopment.PizzariaChatGPT.dto.MetaPackageDTO.NotificationMetaDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/webhook")
@Log4j2
public class MetaWebHookController {
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
            return HttpStatus.OK;
      }

}

