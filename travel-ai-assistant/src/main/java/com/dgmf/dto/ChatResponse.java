package com.dgmf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

// This class represents a chat response with a message
@Data @AllArgsConstructor
public class ChatResponse {
    private String response;
}
