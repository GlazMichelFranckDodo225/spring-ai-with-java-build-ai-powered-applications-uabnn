package com.dgmf.service;

import com.dgmf.dto.ChatRequest;
import com.dgmf.dto.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

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
        // Creating a PromptTemplate with the system prompt
        PromptTemplate promptTemplate = new PromptTemplate(systemPromptTemplate);

        // Creating a system message with specific parameters for the prompt
        Message systemMessage = promptTemplate.createMessage(
                Map.of(
                        "attractionsPerDay", 3,
                        "foodsPerDay", 2,
                        "maxWords", 50
                )
        );

        // Creating a Prompt with the system message and the user's message
        Prompt prompt = new Prompt(
                systemMessage,
                new UserMessage(chatRequest.getMessage())
        );

        // Sending the prompt to the AI model and retrieving the response
        String aiResponse = chatClient
                .prompt(prompt)
                .call()
                .content();

        // Returning the AI response wrapped in a ChatResponse object
        return new ChatResponse(aiResponse);
    }
}
