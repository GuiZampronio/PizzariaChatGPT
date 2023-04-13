package com.webdevelopment.PizzariaChatGPT.dto.MetaPackageDTO;

import lombok.Data;

import java.util.ArrayList;

@Data
public class EntryDTO {
        private String id;
        private ArrayList<ChangeDTO> changes;
}
