package com.dgmf.infiproton.ai.service;

import com.dgmf.infiproton.ai.dto.ChatRequest;
import com.dgmf.infiproton.ai.dto.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TravelChatService {
    private final ChatClient chatClient;

    public ChatResponse chat(ChatRequest chatRequest) {
        String aiResponse = chatClient
                .prompt().
                user(chatRequest.getMessage())
                .call().
                content();

        return new ChatResponse(aiResponse);
    }
}
