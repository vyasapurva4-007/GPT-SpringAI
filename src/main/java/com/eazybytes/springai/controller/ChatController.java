package com.eazybytes.springai.controller;

import com.eazybytes.springai.advisors.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatController {

    public record ChatRequest(String text) {}
    public record ChatResponse(String output) {}

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody ChatRequest request) {
        String reply = chatClient
                .prompt()
                //.advisors(new TokenUsageAuditAdvisor())   // remove if the advisor isn't ready
                .system("You are a helpful assistant.")   // or delete this line
                .user(request.text())
                .call()
                .content();
        return new ChatResponse(reply);
    }
}