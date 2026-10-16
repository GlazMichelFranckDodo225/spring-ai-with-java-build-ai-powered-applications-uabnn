package com.dgmf.service;

import com.dgmf.dto.ItineraryRequest;
import com.dgmf.dto.ItineraryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

// This class provides itinerary-related services using AI chat client.
@Service
@RequiredArgsConstructor
public class ItineraryService {
    // Implements itinerary-related services using AI chat client.
    private final ChatClient chatClient;
    // Template for system prompt used in itinerary-related services.
    @Value("classpath:prompts/travel-system-prompt.st")
    private Resource systemPromptTemplate;

    // This method prepares an itinerary based on the given request.
    public ItineraryResponse prepareItinerary(
            ItineraryRequest itineraryRequest
    ) {
        // This line creates a PromptTemplate with the system prompt
        PromptTemplate promptTemplate = new PromptTemplate(systemPromptTemplate);

        // Creating a system message with specific parameters for the prompt
        Message systemMessage = promptTemplate.createMessage(
                Map.of(
                        "attractionsPerDay", 3,
                        "foodsPerDay", 2,
                        "maxWords", 50
                )
        );

        // Creating a user message with specific parameters for the prompt
        String userMessage =
                "Create a {days}-day itinerary for {destination}";

        // Creates a Message with the user's message and parameters
        int days = itineraryRequest != null ? itineraryRequest.getDays() : 0;
        String destination = (itineraryRequest != null && itineraryRequest.getDestination() != null)
                ? itineraryRequest.getDestination()
                : "";

        Message input = new PromptTemplate(userMessage).createMessage(
                Map.of(
                        "days", days,
                        "destination", destination
                )
        );

        // Creates a Prompt with the system message and the user's message
        Prompt prompt = new Prompt(systemMessage, input);

        // Calls the chat client with the prompt and returns the itinerary response
        return chatClient
                .prompt(prompt)
                .call()
                .entity(ItineraryResponse.class);
    }
}
