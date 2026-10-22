package com.dgmf.service;

import com.dgmf.dto.ChatRequest;
import com.dgmf.dto.ChatResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TravelChatServiceTest {

    @Mock
    private ChatClient chatClient;

    @Mock
    private ChatMemory chatMemory;

    @Mock
    private ChatClient.ChatClientRequestSpec requestSpec;

    @Mock
    private ChatClient.AdvisorSpec advisorSpec;

    @Mock
    private ChatClient.CallResponseSpec responseSpec;

    private TravelChatService travelChatService;

    @BeforeEach
    void setUp() {
        travelChatService = new TravelChatService(chatClient, chatMemory);
        ReflectionTestUtils.setField(
                travelChatService,
                "systemPromptTemplate",
                new ByteArrayResource("You are a travel assistant.".getBytes())
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void chat_withValidRequest_returnsResponse() {
        ChatRequest chatRequest = new ChatRequest();
        chatRequest.setMessage("Recommend a hotel in Paris");

        when(chatClient.prompt()).thenReturn(requestSpec);
        when(requestSpec.advisors(any(Consumer.class))).thenAnswer(invocation -> {
            Consumer<ChatClient.AdvisorSpec> consumer = invocation.getArgument(0);
            when(advisorSpec.advisors(any(org.springframework.ai.chat.client.advisor.api.Advisor[].class))).thenReturn(advisorSpec);
            when(advisorSpec.param(anyString(), any())).thenReturn(advisorSpec);
            consumer.accept(advisorSpec);
            return requestSpec;
        });
        when(requestSpec.system(any(org.springframework.core.io.Resource.class))).thenReturn(requestSpec);
        when(requestSpec.user(anyString())).thenReturn(requestSpec);
        when(requestSpec.call()).thenReturn(responseSpec);
        when(responseSpec.content()).thenReturn("Hotel Le Meurice is a great choice.");

        ChatResponse response = travelChatService.chat(chatRequest);

        assertNotNull(response);
        assertEquals("Hotel Le Meurice is a great choice.", response.getResponse());
        verify(chatClient).prompt();
    }
}
