package com.dgmf.service;

import com.dgmf.dto.ChatRequest;
import com.dgmf.dto.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

// Service for handling travel-related chat interactions
@Service
@RequiredArgsConstructor
public class TravelChatService {
    // Injecting the ChatClient to interact with the AI model
    private final ChatClient chatClient;
    // Injecting the system prompt template for travel-related conversations
    @Value("classpath:prompts/travel-system-prompt.st")
    private Resource systemPromptTemplate;
    // Injecting the ChatMemory to manage conversation context and history
    private final ChatMemory chatMemory;

    // Method to handle chat requests and generate AI responses
    public ChatResponse chat(ChatRequest chatRequest) {
        // Extracting the user's message from the ChatRequest object, ensuring it's not null
        String message = (chatRequest != null && chatRequest.getMessage() != null)
                ? chatRequest.getMessage()
                : "";

        // Calling the ChatClient to generate a response based on the user's message
        String aiResponse = chatClient
                // Setting up the prompt with advisors and system/user messages
                .prompt()
                // Adding a MessageChatMemoryAdvisor to manage chat memory and conversation context
                .advisors(
                                    // Configuring the MessageChatMemoryAdvisor
                        advisorSpec ->
                            // Building the MessageChatMemoryAdvisor with the chat memory
                            advisorSpec
                                    // Creating a MessageChatMemoryAdvisor with the provided
                                    // chat memory
                                    .advisors(
                                            // Building the MessageChatMemoryAdvisor with the chat
                                            // memory
                                            MessageChatMemoryAdvisor.
                                                    // Creating a builder for the
                                                    // MessageChatMemoryAdvisor with the chat memory
                                                    builder(chatMemory)
                                                    // Building the MessageChatMemoryAdvisor instance
                                                    .build()
                                    )
                            // Setting the conversation ID for the chat memory advisor
                            .param(chatMemory.CONVERSATION_ID, "1234")
                )
                // Setting the system prompt template for the AI model
                .system(systemPromptTemplate)
                // Setting the user's message to be processed by the AI model
                .user(message)
                // Calling the AI model to generate a response based on the provided prompts
                // and messages
                .call()
                // Extracting the content of the AI response
                .content();

        // Returning the AI response wrapped in a ChatResponse object
        return new ChatResponse(aiResponse);
    }
}
