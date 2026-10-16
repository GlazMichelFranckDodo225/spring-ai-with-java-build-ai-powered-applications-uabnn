package com.dgmf.dto;

import lombok.Data;

// This class represents a request for an itinerary with
// a destination and number of days
@Data
public class ItineraryRequest {
    private String destination;
    private int days;
}
