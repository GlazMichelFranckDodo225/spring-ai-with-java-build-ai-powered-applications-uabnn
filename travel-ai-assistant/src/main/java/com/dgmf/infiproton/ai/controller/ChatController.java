package com.dgmf.infiproton.ai.controller;

import com.dgmf.infiproton.ai.dto.ChatRequest;
import com.dgmf.infiproton.ai.dto.ChatResponse;
import com.dgmf.infiproton.ai.service.TravelChatService;
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
