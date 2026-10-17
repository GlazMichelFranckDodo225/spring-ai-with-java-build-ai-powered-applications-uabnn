package com.dgmf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

// This class represents a response for an itinerary with
// a destination, a list of itinerary days, and a summary
@Data @AllArgsConstructor
public class ItineraryResponse {
    // The destination of the itinerary
    private String destination;
    // The list of days in the itinerary
    private List<ItineraryDay> itinerary;
    // A summary of the itinerary
    private String summary;
}
