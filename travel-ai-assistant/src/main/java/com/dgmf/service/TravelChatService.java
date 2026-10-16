package com.dgmf.service;

import com.dgmf.dto.ChatRequest;
import com.dgmf.dto.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

// Service for handling travel-related chat interactions
@Service
@RequiredArgsConstructor
public class TravelChatService {
    // Injecting the ChatClient to interact with the AI model
    private final ChatClient chatClient;

    @Value("classpath:prompts/travel-system-prompt.st")
    private Resource systemPromptTemplate;

    // Method to handle chat requests and generate AI responses
    public ChatResponse chat(ChatRequest chatRequest) {
        String message = (chatRequest != null && chatRequest.getMessage() != null)
                ? chatRequest.getMessage()
                : "";

        // Sending the prompt to the AI model and retrieving the response
        String aiResponse = chatClient
                .prompt()
                .system(systemPromptTemplate)
                .user(message)
                .call()
                .content();

        // Returning the AI response wrapped in a ChatResponse object
        return new ChatResponse(aiResponse);
    }
}
