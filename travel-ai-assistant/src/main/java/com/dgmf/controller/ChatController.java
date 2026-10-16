package com.dgmf.controller;

import com.dgmf.dto.ChatRequest;
import com.dgmf.dto.ChatResponse;
import com.dgmf.dto.ItineraryRequest;
import com.dgmf.dto.ItineraryResponse;
import com.dgmf.service.ItineraryService;
import com.dgmf.service.TravelChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

// This class handles chat-related requests
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatController {
    // These lines initialize the TravelChatService and ItineraryService
    private final TravelChatService travelChatService;
    private final ItineraryService itineraryService;

    // Handles chat requests
    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody ChatRequest chatRequest) {
        return travelChatService.chat(chatRequest);
    }

    // Handles itinerary requests
    @PostMapping("/itinerary")
    public ItineraryResponse generateItinerary(
            @RequestBody ItineraryRequest itineraryRequest
            ) {
        return itineraryService.prepareItinerary(itineraryRequest);
    }


}
