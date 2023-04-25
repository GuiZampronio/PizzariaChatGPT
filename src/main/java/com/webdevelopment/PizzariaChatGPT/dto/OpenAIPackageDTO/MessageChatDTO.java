package com.webdevelopment.PizzariaChatGPT.dto.OpenAIPackageDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageChatDTO {
        private String role;
        private String content;
}
