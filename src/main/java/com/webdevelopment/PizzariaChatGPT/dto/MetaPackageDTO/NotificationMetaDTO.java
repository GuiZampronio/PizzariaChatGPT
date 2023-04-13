package com.webdevelopment.PizzariaChatGPT.dto.MetaPackageDTO;

import lombok.Data;

import java.util.ArrayList;

@Data
public class NotificationMetaDTO {
        private String object;
        private ArrayList<EntryDTO> entry;

}
