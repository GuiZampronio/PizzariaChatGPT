package com.webdevelopment.PizzariaChatGPT.dto.OpenAIPackageDTO;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Data
@Builder
public class RequestBodyChatGPTDTO {
        private String model;
        private ArrayList<MessageChatDTO> messages;

        private Double temperature;

        private Integer max_tokens;
}
