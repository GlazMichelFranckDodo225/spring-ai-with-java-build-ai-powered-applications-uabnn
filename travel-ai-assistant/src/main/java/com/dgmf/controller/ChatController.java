package com.dgmf.controller;

import com.dgmf.dto.ChatRequest;
import com.dgmf.dto.ChatResponse;
import com.dgmf.dto.ItineraryRequest;
import com.dgmf.dto.ItineraryResponse;
import com.dgmf.service.ItineraryService;
import com.dgmf.service.TravelChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

// This class is a REST controller that handles chat and itinerary requests
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatController {
    // Injects the TravelChatService and ItineraryService
    private final TravelChatService travelChatService;
    private final ItineraryService itineraryService;

    // Handles chat requests
    @PostMapping("/chat")
    public ChatResponse chat(
            // Receives a ChatRequest object from the request body
            @RequestBody ChatRequest chatRequest
    ) {
        // Delegates the chat request to the TravelChatService and returns the response
        return travelChatService.chat(chatRequest);
    }

    // Handles itinerary generation requests
    @PostMapping("/itinerary")
    public ItineraryResponse generateItinerary(
            // Receives an ItineraryRequest object from the request body
            @RequestBody ItineraryRequest itineraryRequest
            )
    {
        // Delegates the itinerary request to the ItineraryService to prepare a draft itinerary
        ItineraryResponse draftItinerary = itineraryService.prepareItinerary(itineraryRequest);

        // Delegates the draft itinerary to the ItineraryService to improve it and
        // returns the final itinerary
        return itineraryService.improveItinerary(draftItinerary);
    }


}
