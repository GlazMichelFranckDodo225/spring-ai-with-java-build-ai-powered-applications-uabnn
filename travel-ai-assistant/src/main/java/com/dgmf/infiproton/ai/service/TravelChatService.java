package com.dgmf.infiproton.ai.service;

import com.dgmf.infiproton.ai.dto.ChatRequest;
import com.dgmf.infiproton.ai.dto.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TravelChatService {
    private final ChatClient chatClient;
    private final String SYSTEM_PROMPT = """
                        You are an AI Travel Assistant.
                        
                        Your responsibility:
                            - Help users plan trips and vacations.
                            - Keep responses friendly and concise.
                            
                        When creating itineraries:
                            - Recommend exactly 2 attractions per day.
                            - Recommend 1 local food for each day.
                            - Keep each day's description under 50 words.
                        """;

    public ChatResponse chat(ChatRequest chatRequest) {
        Prompt prompt = new Prompt(
                new SystemMessage(SYSTEM_PROMPT),
                new UserMessage(chatRequest.getMessage())
        );

        String aiResponse = chatClient
                .prompt(prompt)
                .call()
                .content();

        return new ChatResponse(aiResponse);
    }
}
