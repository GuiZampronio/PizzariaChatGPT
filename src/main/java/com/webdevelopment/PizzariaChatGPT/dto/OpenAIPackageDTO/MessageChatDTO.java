package com.webdevelopment.PizzariaChatGPT.dto.OpenAIPackageDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MessageChatDTO {
        private String role;
        private String content;
}
