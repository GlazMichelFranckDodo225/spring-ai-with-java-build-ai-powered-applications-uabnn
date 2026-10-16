package com.dgmf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

// This class represents a response for an itinerary with
// a destination and an itinerary
@Data @AllArgsConstructor
public class ItineraryResponse {
    private String destination;
    private List<ItineraryDay> itinerary;
}
