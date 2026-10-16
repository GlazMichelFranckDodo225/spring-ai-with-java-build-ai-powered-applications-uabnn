package com.dgmf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

// This class represents a day in an itinerary with
// attractions, food, and day number
@Data @AllArgsConstructor
public class ItineraryDay {
    private int day;
    private List<String> attractions;
    private String food;
}
