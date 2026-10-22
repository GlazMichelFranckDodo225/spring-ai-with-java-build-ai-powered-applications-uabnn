package com.dgmf.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Configuration class for setting up AI-related beans
@Configuration
public class AIConfig {
    // Bean for managing chat memory with a maximum of 20 messages
    @Bean
    public ChatMemory chatMemory() {
        // Using MessageWindowChatMemory to store the last 20
        // messages in the chat
        return MessageWindowChatMemory
                // Creating a builder for the MessageWindowChatMemory
                .builder()
                // Setting the maximum number of messages to store
                // in memory
                .maxMessages(20)
                // Building the ChatMemory instance
                .build();
    }

    // Bean for creating a ChatClient instance using the
    // provided builder
    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        // Building the ChatClient instance using the provided builder
        return builder.build();
    }
}
