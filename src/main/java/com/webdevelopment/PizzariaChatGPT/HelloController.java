package com.webdevelopment.PizzariaChatGPT;

import com.webdevelopment.PizzariaChatGPT.service.OpenAIRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private OpenAIRestService openAIRestService;

    @GetMapping("/hello")
    public String sayHello(){
        //openAIRestService.sendMessageChatGPT();
        return "OK";
    }}
