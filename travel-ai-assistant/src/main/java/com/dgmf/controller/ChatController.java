package com.dgmf.controller;

import com.dgmf.dto.ChatRequest;
import com.dgmf.dto.ChatResponse;
import com.dgmf.dto.ItineraryRequest;
import com.dgmf.dto.ItineraryResponse;
import com.dgmf.service.ItineraryService;
import com.dgmf.service.TravelChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// This class is a REST controller that handles chat and itinerary
// requests
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatController {
    // Injecting the TravelChatService to handle chat-related operations
    private final TravelChatService travelChatService;
    // Injecting the ItineraryService to handle itinerary-related
    // operations
    private final ItineraryService itineraryService;
    // Injecting the ChatMemory to manage conversation context and history
    private final ChatMemory chatMemory;

    // Handles chat requests
    @PostMapping("/chat")
    public ChatResponse chat(
            // Receives a ChatRequest object from the request body
            @RequestBody ChatRequest chatRequest
    ) {
        // Delegates the chat request to the TravelChatService
        // and returns the response
        return travelChatService.chat(chatRequest);
    }

    // Handles itinerary generation requests
    @PostMapping("/itinerary")
    public ItineraryResponse generateItinerary(
            // Receives an ItineraryRequest object from the
            // request body
            @RequestBody ItineraryRequest itineraryRequest
            )
    {
        // Delegates the itinerary request to the ItineraryService
        // to prepare a draft itinerary
        ItineraryResponse draftItinerary = itineraryService
                .prepareItinerary(itineraryRequest);

        // Delegates the draft itinerary to the ItineraryService to
        // improve it and returns the final itinerary
        return itineraryService.improveItinerary(draftItinerary);
    }

    // Endpoint to fetch the chat memory for a specific conversation
    @GetMapping("/memory")
    public List<Message> fetchMemory() {
        // Retrieves the chat memory for the conversation with
        // ID "1234" and returns it as a list of messages
        return chatMemory.get("1234");
    }
}
