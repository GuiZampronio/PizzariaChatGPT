package com.webdevelopment.PizzariaChatGPT.dto.OpenAIPackageDTO;

import lombok.Data;

import java.util.ArrayList;

@Data
public class GPTResponse {
        private ArrayList<ChoicesDTO> choices;
}
