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
                .prompt()
                .system("""
                        You are an AI Travel Assistant.
                        
                        Your responsibility:
                            - Help users plan trips and vacations.
                            - Keep responses friendly and concise.
                            
                        When creating itineraries:
                            - Recommend exactly 2 attractions per day.
                            - Recommend 1 local food for each day.
                            - Keep each day's description under 50 words.
                        """)
                .user(chatRequest.getMessage())
                .call()
                .content();

        return new ChatResponse(aiResponse);
    }
}
