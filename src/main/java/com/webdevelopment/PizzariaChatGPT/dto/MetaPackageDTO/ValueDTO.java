package com.webdevelopment.PizzariaChatGPT.dto.MetaPackageDTO;

import lombok.Data;

import java.lang.reflect.Array;
import java.util.ArrayList;

@Data
public class ValueDTO {
        private String messaging_product;
        private MetaDataDTO metadata;
        private ArrayList<ContactDTO> contacts;
        private ArrayList<MessageDTO> messages;

}
