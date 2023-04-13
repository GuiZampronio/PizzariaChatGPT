package com.webdevelopment.PizzariaChatGPT.dto.MetaPackageDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendMessageDTO {
    private String messaging_product;
    private String recipient_type;
    private String to;
    private String type;
    private TextDTO text;
}
