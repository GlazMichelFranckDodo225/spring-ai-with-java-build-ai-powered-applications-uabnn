package com.dgmf.service;

import com.dgmf.dto.ItineraryDay;
import com.dgmf.dto.ItineraryRequest;
import com.dgmf.dto.ItineraryResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItineraryServiceTest {

    @Mock
    private ChatClient chatClient;

    @Mock
    private ChatClient.ChatClientRequestSpec requestSpec;

    @Mock
    private ChatClient.CallResponseSpec responseSpec;

    private ItineraryService itineraryService;

    @BeforeEach
    void setUp() {
        itineraryService = new ItineraryService(chatClient);
        ReflectionTestUtils.setField(
                itineraryService,
                "systemPromptTemplate",
                new ByteArrayResource("Attractions: {attractionsPerDay}, Foods: {foodsPerDay}, Words: {maxWords}".getBytes())
        );
    }

    @Test
    void prepareItinerary_withValidRequest() {
        ItineraryRequest request = new ItineraryRequest();
        request.setDestination("Tokyo");
        request.setDays(3);

        ItineraryResponse expectedResponse = new ItineraryResponse("Tokyo", List.of(new ItineraryDay(1, List.of("Senso-ji"), "Ramen")));

        when(chatClient.prompt(any(Prompt.class))).thenReturn(requestSpec);
        when(requestSpec.call()).thenReturn(responseSpec);
        when(responseSpec.entity(ItineraryResponse.class)).thenReturn(expectedResponse);

        ItineraryResponse actualResponse = itineraryService.prepareItinerary(request);

        assertNotNull(actualResponse);
        assertEquals("Tokyo", actualResponse.getDestination());
        verify(chatClient).prompt(any(Prompt.class));
    }

    @Test
    void prepareItinerary_withNullDestination() {
        ItineraryRequest request = new ItineraryRequest();
        request.setDestination(null);
        request.setDays(2);

        ItineraryResponse expectedResponse = new ItineraryResponse("", List.of());

        when(chatClient.prompt(any(Prompt.class))).thenReturn(requestSpec);
        when(requestSpec.call()).thenReturn(responseSpec);
        when(responseSpec.entity(ItineraryResponse.class)).thenReturn(expectedResponse);

        ItineraryResponse actualResponse = itineraryService.prepareItinerary(request);

        assertNotNull(actualResponse);
        verify(chatClient).prompt(any(Prompt.class));
    }

    @Test
    void prepareItinerary_withNullRequest() {
        ItineraryResponse expectedResponse = new ItineraryResponse("", List.of());

        when(chatClient.prompt(any(Prompt.class))).thenReturn(requestSpec);
        when(requestSpec.call()).thenReturn(responseSpec);
        when(responseSpec.entity(ItineraryResponse.class)).thenReturn(expectedResponse);

        ItineraryResponse actualResponse = itineraryService.prepareItinerary(null);

        assertNotNull(actualResponse);
        verify(chatClient).prompt(any(Prompt.class));
    }
}
