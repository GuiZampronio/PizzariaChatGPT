package com.webdevelopment.PizzariaChatGPT.dto.MetaPackageDTO;

import lombok.Data;

@Data
public class MessageDTO {
        private String id;
        private String from;
        private String timestamp;
        private String type;
        private  TextDTO text;

}
