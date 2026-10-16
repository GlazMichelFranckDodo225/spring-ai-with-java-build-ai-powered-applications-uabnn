package com.dgmf.controller;

import com.dgmf.dto.ChatRequest;
import com.dgmf.dto.ChatResponse;
import com.dgmf.service.TravelChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatController {
    private final TravelChatService travelChatService;

    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody ChatRequest chatRequest) {
        return travelChatService.chat(chatRequest);
    }
}
