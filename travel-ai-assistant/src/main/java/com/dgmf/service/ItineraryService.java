package com.dgmf.service;

import com.dgmf.dto.ItineraryRequest;
import com.dgmf.dto.ItineraryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

// Service for handling itinerary-related operations
@Slf4j
@Service
@RequiredArgsConstructor
public class ItineraryService {
    // Injecting the ChatClient to interact with the AI model
    private final ChatClient chatClient;
    // Injecting the system prompt template for itinerary generation
    @Value("classpath:prompts/travel-system-prompt.st")
    private Resource systemPromptTemplate;
    // ObjectMapper for converting objects to JSON strings
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Method to prepare an itinerary based on the provided request
    public ItineraryResponse prepareItinerary(
            // The ItineraryRequest object containing the destination and number of days
            ItineraryRequest itineraryRequest
    ) {
        // Creating a PromptTemplate from the system prompt template resource
        PromptTemplate promptTemplate = new PromptTemplate(systemPromptTemplate);

        // Creating a system message with specific parameters for the prompt
        Message systemMessage = promptTemplate.createMessage(
                Map.of(
                        "attractionsPerDay", 3,
                        "foodsPerDay", 2,
                        "maxWords", 50
                )
        );

        // Defining the user's message with placeholders for days and destination
        String userMessage =
                "Create a {days}-day itinerary for {destination}";

        //
        int days = itineraryRequest != null ? itineraryRequest.getDays() : 0;
        String destination = (itineraryRequest != null && itineraryRequest.getDestination() != null)
                ? itineraryRequest.getDestination()
                : "";

        //
        Message input = new PromptTemplate(userMessage).createMessage(
                Map.of(
                        "days", days,
                        "destination", destination
                )
        );

        // Creating a Prompt object with the system message and user input
        Prompt prompt = new Prompt(systemMessage, input);

        // Sending the prompt to the AI model and retrieving the response as
        // an ItineraryResponse object
        return chatClient
                .prompt(prompt)
                .call()
                .entity(ItineraryResponse.class);
    }

    // Method to improve an existing itinerary based on the provided response
    public ItineraryResponse improveItinerary(
            // The ItineraryResponse object containing the existing itinerary to be improved
            ItineraryResponse itineraryResponse
    ) {
        // Converting the ItineraryResponse object to a pretty-printed JSON string
        String itineraryString = objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(itineraryResponse);

        // Logging the existing itinerary for debugging purposes
        log.info("Draft Itinerary: {}", itineraryString);

        // Defining the user's message with instructions to review and improve the itinerary
        String message = """
                Review the following travel itinerary:
                
                Check:
                    - Logical city progression
                    - Travel efficiency
                    - Nearby attractions
                    - Remove unnecessary travel
                    
                Return and improve itinerary using the same JSON structure.
                
                Itinerary:
                {itinerary}
                """;

        // Creating a Message object with the user's message and the existing itinerary
        Message userMessage = new PromptTemplate(message).createMessage(
                Map.of("itinerary", itineraryString)
        );

        // Creating a Prompt object with the system message and user input
        Prompt prompt = new Prompt(userMessage);

        // Sending the prompt to the AI model and retrieving the improved itinerary
        return chatClient
                .prompt(prompt)
                .call()
                .entity(ItineraryResponse.class);
    }
}
